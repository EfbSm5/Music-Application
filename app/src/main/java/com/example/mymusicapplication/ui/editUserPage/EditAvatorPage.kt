package com.example.mymusicapplication.ui.editUserPage

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.core.net.toFile
import coil.compose.AsyncImage
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.Executors


@Composable
fun CameraView(onImageSaved: (Uri) -> Unit) {
    var imageCapture: ImageCapture? by remember { mutableStateOf(null) }
    val context = LocalContext.current

    Column {
        val lifeCycleOwner = LocalLifecycleOwner.current

        AndroidView(
            factory = { ctx ->

                val previewView = PreviewView(ctx)
                val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)

                cameraProviderFuture.addListener(/* listener = */ {
                    val cameraProvider = cameraProviderFuture.get()

                    val preview = Preview.Builder().build()
                        .also { it.setSurfaceProvider(previewView.surfaceProvider) }

                    imageCapture = ImageCapture.Builder().build()

                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        lifecycleOwner = lifeCycleOwner,
                        cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA,
                        preview,
                        imageCapture
                    )

                }, /* executor = */ ContextCompat.getMainExecutor(ctx))

                return@AndroidView previewView
            }, modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        )
        imageCapture?.let { capture ->
            Column {
                Button(onClick = {
                    takePhoto(
                        context = context, imageCapture = capture, onImageSaved = onImageSaved
                    )
                }) {
                    Text("拍照")
                }
            }
        }
    }
}


fun takePhoto(
    context: Context, imageCapture: ImageCapture, onImageSaved: (Uri) -> Unit
) {
    val photoFile = File(
        context.filesDir, SimpleDateFormat(
            "yyyy-MM-dd-HH-mm-ss-SSS", Locale.US
        ).format(System.currentTimeMillis()) + ".jpg"
    )

    val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

    imageCapture.takePicture(/* outputFileOptions = */ outputOptions,/* executor = */
        Executors.newSingleThreadExecutor(),/* imageSavedCallback = */
        object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                val savedUri = outputFileResults.savedUri
                onImageSaved(savedUri!!)
            }

            override fun onError(exception: ImageCaptureException) {
                Log.e("CameraView", "Photo capture failed: ${exception.message}", exception)
            }
        })
}

@OptIn(ExperimentalPermissionsApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PhotoScreen(saveData: (File) -> Unit) {
    var screen: Screen by remember { mutableStateOf(Screen.PermissionDenied) }

    val cameraPermissionState = rememberPermissionState(android.Manifest.permission.CAMERA)

    LaunchedEffect(cameraPermissionState.status) {
        if (!cameraPermissionState.status.isGranted) {
            cameraPermissionState.launchPermissionRequest()
        } else {
            screen = Screen.Capture
        }
    }
    View(saveData = saveData,
        screen = screen,
        onImageSaved = { screen = Screen.Success(uri = it) },
        rePhoto = { screen = Screen.Capture })
}


@Composable
fun View(
    saveData: (File) -> Unit, screen: Screen, onImageSaved: (Uri) -> Unit, rePhoto: () -> Unit
) {
    when (screen) {
        Screen.Capture -> {
            CameraView(onImageSaved = onImageSaved)
        }

        Screen.PermissionDenied -> {
            Text(text = "no permission")
        }

        is Screen.Success -> {
            val savedImage = screen.uri.toFile()
            saveData(savedImage)
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(model = savedImage, contentDescription = null)
                Spacer(modifier = Modifier.height(50.dp))
                Button(
                    onClick = { rePhoto() },
                ) {
                    Text(text = "重拍")
                }
            }
        }
    }
}

sealed interface Screen {
    data object PermissionDenied : Screen
    data object Capture : Screen
    data class Success(val uri: Uri) : Screen
}

