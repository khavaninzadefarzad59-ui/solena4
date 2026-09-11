package com.example.solenaassistant

import android.app.Activity
import android.app.role.RoleManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AssistantRoleScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssistantRoleScreen() {
    val context = LocalContext.current
    val roleManager = remember {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            context.getSystemService(RoleManager::class.java)
        } else {
            null
        }
    }

    // استعلام وضعیت جاری نقش دستیار در اندروید ۱۰
    fun queryRoleHeld(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && roleManager != null) {
            roleManager.isRoleHeld(RoleManager.ROLE_ASSISTANT)
        } else {
            false
        }
    }

    var isRoleHeld by remember { mutableStateOf(queryRoleHeld()) }
    var statusMessage by remember { mutableStateOf("") }

    // ثبت لانچر درخواست نقش با استفاده از RoleManager اندروید ۱۰
    val requestRoleLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val held = queryRoleHeld()
        isRoleHeld = held
        statusMessage = if (held) {
            "Solena Assistant Test با موفقیت به عنوان دستیار پیش‌فرض انتخاب شد."
        } else {
            "درخواست دستیار تأیید نشد یا توسط کاربر لغو شد."
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Solena Assistant Test",
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // کارت نمایش وضعیت نقش دستیار
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isRoleHeld) {
                        Color(0xFFE8F5E9)
                    } else {
                        Color(0xFFFFEBEE)
                    }
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "وضعیت نقش دستیار سیستم (Assistant Role):",
                        fontSize = 15.sp,
                        color = Color(0xFF37474F)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = if (isRoleHeld) "فعال (دستیار پیش‌فرض)" else "غیرفعال (پیش‌فرض نیست)",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isRoleHeld) Color(0xFF2E7D32) else Color(0xFFC62828)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = if (isRoleHeld) {
                            "RoleManager.isRoleHeld(ROLE_ASSISTANT) == true"
                        } else {
                            "RoleManager.isRoleHeld(ROLE_ASSISTANT) == false"
                        },
                        fontSize = 12.sp,
                        color = Color(0xFF78909C)
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // دکمه درخواست تنظیم به عنوان دستیار با RoleManager اندروید ۱۰
            Button(
                onClick = {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && roleManager != null) {
                        if (roleManager.isRoleAvailable(RoleManager.ROLE_ASSISTANT)) {
                            if (!roleManager.isRoleHeld(RoleManager.ROLE_ASSISTANT)) {
                                val intent: Intent = roleManager.createRequestRoleIntent(RoleManager.ROLE_ASSISTANT)
                                requestRoleLauncher.launch(intent)
                            } else {
                                statusMessage = "برنامه هم‌اکنون به عنوان دستیار پیش‌فرض تنظیم است."
                            }
                        } else {
                            statusMessage = "نقش ROLE_ASSISTANT در این دستگاه در دسترس نیست."
                        }
                    } else {
                        // فال‌بک برای نسخه‌های قبل از اندروید ۱۰
                        val intent = Intent(Settings.ACTION_VOICE_INPUT_SETTINGS)
                        context.startActivity(intent)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "تنظیم به عنوان دستیار (Request Role)",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            if (statusMessage.isNotEmpty()) {
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = statusMessage,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}