# ESP32 Bluetooth Controller (Android)

A simple Android app built with Jetpack Compose to control an ESP32 device via Bluetooth.

This project is part of **The CodeSmithy** channel.

---

## 🚀 Features

- Connect to ESP32 via Bluetooth
- Send ON/OFF commands
- Simple UI with Jetpack Compose
- Runtime Bluetooth permission handling (Android 12+)

---

## 🔧 Requirements

- Android device with Bluetooth
- Paired ESP32 device named: `ESP32_LED`
- ESP32 running compatible Bluetooth Serial code

---

## 📱 How It Works

The app connects to an ESP32 device using Bluetooth SPP and sends simple commands:

| Command | Action |
|--------|--------|
| `1` | Turn LED ON |
| `0` | Turn LED OFF |

---

## ⚙️ Setup

1. Pair your phone with ESP32 (`ESP32_LED`)
2. Open the app
3. Grant Bluetooth permissions
4. Tap **Connect**
5. Use switch to control LED

---

## 📌 Notes

- Works with ESP32 Bluetooth Serial
- Requires Android 12+ permissions handling
- Device name must match `ESP32_LED`

---

## 📌 Author

**The CodeSmithy**  
Android • IoT • Embedded Projects
