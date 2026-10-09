# GlowLive Android starter

An original-branded Android Studio prototype inspired by the supplied live-chat profile screenshot.

## Included
- Profile screen with follower counts, coins and points
- Bottom navigation: Rooms, Moments, Messages, Me
- Sample live-room list and join navigation
- Demo messaging UI
- Daily tasks with locally claimable demo rewards
- Wallet screen with demo coin increments
- Host Center and Agency screens
- Admin dashboard sample view

## Open and run
1. Install Android Studio (current stable) with Android SDK Platform 35.
2. Open this `GlowLiveAndroid` folder in Android Studio.
3. Let Gradle sync finish.
4. Run the `app` configuration on an Android emulator or USB-connected phone.

## Important: prototype, not a production service
This project is a runnable UI prototype. It does **not** yet provide actual internet chat, audio/video live streaming, real user authentication, cloud storage, real coin sales, or real withdrawals. Rewards and balances are local demo state and may reset when the app process is recreated. The admin screen is a demo and is not secure authorization.

Before launching publicly, add a backend (for example Firebase or your own API), authentication and role-based access, database rules, moderation/reporting, streaming infrastructure, secure payment/payout integration, audit logs, privacy policy, and applicable legal compliance. Never trust coin balances or admin privileges stored only on the phone.

## Build an installable APK using GitHub Actions
This project includes `.github/workflows/android.yml`, which builds a debug APK in GitHub Actions.

1. Create a GitHub repository and upload the project files so `settings.gradle.kts` is at the repository root.
2. Open the repository's **Actions** tab and run **Build GlowLive APK** (or push to the `main` branch).
3. When the workflow finishes successfully, open its run and download the `GlowLive-debug-apk` artifact.
4. Extract the downloaded artifact on your phone and install `app-debug.apk`. Android may ask you to allow installs from that source.

The APK produced this way is a debug build for testing, not a Play Store release. Real online features and real-money payments are not implemented.
