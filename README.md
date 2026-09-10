# Solena Assistant Test (Android 10 / API 29)

پروژه نیتیو اندروید حداقلی برای تست فنی:
- هدف: Android 10 (API 29)
- زبان: Kotlin
- رابط کاربری: Jetpack Compose
- بیلد: Gradle با Kotlin DSL (`build.gradle.kts`)
- سرویس صوتی: `SolenaVoiceInteractionService`
- تنظیم دستیار: `RoleManager.ROLE_ASSISTANT` (مخصوص اندروید ۱۰ به بالا)

## نحوه تست در شبیه‌ساز یا دستگاه حقیقی
```bash
# 1. بیلد و نصب برنامه
./gradlew installDebug

# 2. بررسی دستیار صوتی فعال فعلی سیستم
adb shell settings get secure voice_interaction_service

# 3. تست دادن نقش با RoleManager از طریق دستور مستقیم ADB (اختیاری)
adb shell cmd role add-role-holder android.app.role.ASSISTANT com.example.solenaassistant

# 4. بررسی دارنده فعلی نقش دستیار
adb shell cmd role get-role-holders android.app.role.ASSISTANT
```
