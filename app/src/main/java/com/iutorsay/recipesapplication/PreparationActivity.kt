package com.iutorsay.recipesapplication

import ai.snips.hermes.IntentMessage
import ai.snips.platform.SnipsPlatformClient
import android.Manifest
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.pm.PackageManager
import android.databinding.DataBindingUtil
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.util.Log
import com.iutorsay.recipesapplication.data.entities.Recipe
import com.iutorsay.recipesapplication.data.repositories.StepRepository
import com.iutorsay.recipesapplication.databinding.ActivityPreparationBinding
import com.iutorsay.recipesapplication.viewmodels.PreparationViewModel
import kotlinx.android.synthetic.main.activity_preparation.*
import java.io.*
import java.util.zip.ZipInputStream

class PreparationActivity : AppCompatActivity() {
    private val TAG = "__SNIPS"
    private lateinit var assistantLocation: File
    private lateinit var preparationViewModel: PreparationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding : ActivityPreparationBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_preparation)

        val recipe2 = intent.getSerializableExtra("recipe") as Recipe
        val steps2 = StepRepository.getInstance().getRecipeInstructions(recipe2.recipeId)

        preparationViewModel = ViewModelProviders.of(this).get(PreparationViewModel::class.java)

        binding.viewmodel = preparationViewModel
        binding.setLifecycleOwner(this)

        steps2.observe(this, Observer { list ->
            Log.d("__STEPS", list.toString())
            preparationViewModel.apply {
                recipe = recipe2
                steps.value = list
                currentStep.value = list?.first()
            }

            preparationViewModel.currentStep.value?.let {
                preparationViewModel.title.value = "Étape ${preparationViewModel.steps.value?.indexOf(it)?.plus(1)} sur ${preparationViewModel.steps.value?.count()}"
                it.timing?.let { timing ->
                    preparationViewModel.currentTiming.value = "Durée: $timing minutes"
                }
            }
        })

        next.setOnClickListener { next() }

        previous.setOnClickListener { previous() }

        assistantLocation = File(filesDir, "snips")
        extractAssistantIfNeeded(assistantLocation)
        if (ensurePermissions()) {
            startSnips(assistantLocation)
        }
    }

    private fun next() {
        val nextIndex = preparationViewModel.steps.value?.indexOf(preparationViewModel.currentStep.value)?.plus(1)
        val nextStep = preparationViewModel.steps.value?.getOrNull(nextIndex!!)

        if (nextStep != null) {
            preparationViewModel.apply {
                currentStep.value = nextStep
                title.value = "Étape ${this.steps.value?.indexOf(nextStep)?.plus(1)} sur ${this.steps.value?.count()}"
            }

            preparationViewModel.currentStep.value?.timing?.let { timing ->
                preparationViewModel.currentTiming.value = "Durée: $timing minutes"
            }
        }
    }

    private fun previous() {
        val previousIndex = preparationViewModel.steps.value?.indexOf(preparationViewModel.currentStep.value)?.minus(1)
        val previousStep = preparationViewModel.steps.value?.getOrNull(previousIndex!!)

        if (previousStep != null) {
            preparationViewModel.apply {
                currentStep.value = previousStep
                title.value = "Étape ${this.steps.value?.indexOf(previousStep)?.plus(1)} sur ${this.steps.value?.count()}"
            }

            preparationViewModel.currentStep.value?.timing?.let { timing ->
                preparationViewModel.currentTiming.value = "Durée: $timing minutes"
            }
        }
    }

    private fun ensurePermissions(): Boolean {
        val status = ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.RECORD_AUDIO
        )
        if (status != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_EXTERNAL_STORAGE),
                0
            )
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 0 && grantResults.size > 0 &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            startSnips(assistantLocation)
        }
    }

   private fun startSnips(snipsDir: File) {
        val client = createClient(snipsDir)
        client.connect(this.applicationContext)
    }

    private fun extractAssistantIfNeeded(assistantLocation: File) {
        val versionFile = File(
            assistantLocation,
            "android_version_" + BuildConfig.VERSION_NAME
        )

        if (versionFile.exists()) {
            Log.d(TAG, "Assistant déjà extrait")
            return
        }

        try {
            assistantLocation.delete()
            UnzipAsyncTask(this).execute()
            versionFile.createNewFile()
        } catch (e: IOException) {
            return
        }
    }

    private fun createClient(assistantLocation: File): SnipsPlatformClient {
        val assistantDir = File(assistantLocation, "assistant")

        val client = SnipsPlatformClient.Builder(assistantDir)
            .enableDialogue(true)
            .enableHotword(true)
            .enableSnipsWatchHtml(false)
            .enableLogs(true)
            .withHotwordSensitivity(0.5f)
            .enableStreaming(false)
            .enableInjection(false)
            .build()

        client.onPlatformReady = fun() {
            Log.d(TAG, "Snips is ready. Say the wake word!")
        }

        client.onPlatformError = fun(snipsPlatformError: SnipsPlatformClient.SnipsPlatformError) {
            // Handle error
            Log.d(TAG, "Error: " + snipsPlatformError.message!!)
        }

        client.onHotwordDetectedListener = fun() {
            // Wake word detected, start a dialog session
            Log.d(TAG, "Wake word detected!")
            client.startSession(
                null, ArrayList(),
                false, null
            )
            jarvisDetection.setAnimation("detectionJarvis.json")
            jarvisDetection.playAnimation()
            jarvisDetection.loop(true)
        }

        client.onIntentDetectedListener = fun(intentMessage: IntentMessage) {
            // Intent detected, so the dialog session ends here
            client.endSession(intentMessage.sessionId, null)
            Log.d(TAG, "Intent detected: " + intentMessage.intent.intentName)

            jarvisDetection.cancelAnimation()
            jarvisDetection.progress = 0f

            when (intentMessage.intent.intentName) {
                "Anqo:NextStep" -> next()
                "Anqo:PreviousStep" -> previous()
            }
        }

        client.onSnipsWatchListener = fun(s: String) {
            Log.d(TAG, "Log: $s")
        }

        return client
    }

    @Throws(IOException::class)
    private fun unzip(zipFile: InputStream, targetDirectory: File) {
        Log.d(TAG, "Extraction de l'assistant")
        ZipInputStream(BufferedInputStream(zipFile)).use { zis ->
            var ze = zis.nextEntry
            var count: Int
            val buffer = ByteArray(8192)
            while (ze != null) {
                val file = File(targetDirectory, ze.name)
                val dir = if (ze.isDirectory) file else file.parentFile
                if (!dir.isDirectory && !dir.mkdirs())
                    throw FileNotFoundException("Failed to create directory: " + dir.absolutePath)
                if (ze.isDirectory) {
                    ze = zis.nextEntry
                    continue
                }
                FileOutputStream(file).use { fout ->
                    count = zis.read(buffer)
                    while (count != -1) {
                        fout.write(buffer, 0, count)
                        count = zis.read(buffer)
                    }
                }
                ze = zis.nextEntry
            }
        }
        Log.d(TAG, "Extraction terminée")
    }

    companion object {
        class UnzipAsyncTask(private val preparationActivity: PreparationActivity) : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg params: Void?): Void? {
                preparationActivity.unzip(
                    preparationActivity.baseContext.assets.open("assistant.zip"),
                    preparationActivity.assistantLocation
                )
                return null
            }
        }
    }
}
