package com.example.mymusicapplication.ui.page

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
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestCameraPermission(onImageCaptureCreated: (ImageCapture) -> Unit) {
    val context = LocalContext.current
    val cameraPermissionState = rememberPermissionState(
        android.Manifest.permission.CAMERA
    )

    LaunchedEffect(key1 = cameraPermissionState) {
        cameraPermissionState.launchPermissionRequest()
    }

    if (cameraPermissionState.status.isGranted) {
        CameraView(onImageCaptureCreated)
    } else {
        Text("Camera permission is required to take pictures.")
    }
}

@Composable
fun CameraView(onImageCaptureCreated: (ImageCapture) -> Unit) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    AndroidView(factory = { ctx ->
        val previewView = PreviewView(ctx)
        val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)

        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(previewView.surfaceProvider)
            }

            val imageCapture = ImageCapture.Builder().build()
            onImageCaptureCreated(imageCapture) // Notify the caller about the ImageCapture instance

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    lifecycleOwner,
                    CameraSelector.DEFAULT_BACK_CAMERA,
                    preview,
                    imageCapture
                )
            } catch (exc: Exception) {
                Log.e("CameraView", "Use case binding failed", exc)
            }

            previewView
        }, ContextCompat.getMainExecutor(ctx))

        previewView
    }, modifier = Modifier.fillMaxSize())
}

@Composable
fun UploadAvatarScreen(imageCapture: ImageCapture) {
    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (imageUri != null) {
            Image(
                painter = rememberAsyncImagePainter(imageUri),
                contentDescription = "Captured Image",
                modifier = Modifier.padding(200.dp)
            )
        }

        Button(onClick = {
            takePhoto(context, imageCapture) { uri ->
                imageUri = uri
            }
        }) {
            Text("Take Photo")
        }

        Button(onClick = {
            // 上传头像逻辑
        }) {
            Text("Upload Avatar")
        }
    }
}

fun takePhoto(context: Context, imageCapture: ImageCapture, onImageCaptured: (Uri) -> Unit) {
    val photoFile = File(
        context.filesDir,
        SimpleDateFormat(
            "yyyy-MM-dd-HH-mm-ss-SSS", Locale.US
        ).format(System.currentTimeMillis()) + ".jpg"
    )

    val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

    imageCapture.takePicture(
        outputOptions,
        ContextCompat.getMainExecutor(context),
        object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                val savedUri = Uri.fromFile(photoFile)
                onImageCaptured(savedUri)
            }

            override fun onError(exception: ImageCaptureException) {
                Log.e("CameraView", "Photo capture failed: ${exception.message}", exception)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen() {
    var imageCapture by remember { mutableStateOf<ImageCapture?>(null) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
    ) {
        item {
            RequestCameraPermission { capture ->
                imageCapture = capture
            }
        }
        item {
            imageCapture?.let { capture ->
                UploadAvatarScreen(imageCapture = capture)
            }
        }
    }
}





