# 📄 Bill Reader App

## 💡 Problem Statement

Many people — especially senior citizens and those who have trouble reading due to visibility issues — face difficulty understanding bill details clearly. This is common with electricity bills, hospital bills, shopping bills, and more.  

The **Bill Reader App** makes it easy for anyone to scan a bill (using the camera or selecting from the gallery), extract the text, and hear it read aloud in **Telugu**. Additionally, the app saves all bills to a cloud database so they can be reviewed anytime.

---

## 🎯 Features

✅ Capture bill image using **camera**.  
✅ Pick bill image from **gallery**.  
✅ Extract text from the image using **Google ML Kit OCR**.  
✅ Read extracted text aloud in **Telugu** using Text-to-Speech (TTS).  
✅ Save bills to **Firebase Firestore** with timestamp.  
✅ View previously saved bills easily.  
✅ **Increase or decrease text size** (zoom in/out) for better readability.  
✅ Simple and friendly UI for all ages.

---

## 🛠️ Tech Stack

- **Android (Java)** — Main app framework.
- **Google ML Kit** — Text recognition (OCR) from images.
- **Firebase Firestore** — Cloud database to securely store extracted bills.
- **Android Text-to-Speech (TTS)** — Supports Telugu reading.
- **Modern UI design** — Clean, accessible, and easy to navigate.

---

## 🏗️ How It Works

1️⃣ User opens the app and chooses **Open Camera** or **Open Gallery**.  
2️⃣ User captures or selects a bill image.  
3️⃣ App extracts text line by line (left to right) for better accuracy.  
4️⃣ Extracted text is displayed on the screen and read aloud in **Telugu**.  
5️⃣ User can **zoom in or zoom out** to adjust text size.  
6️⃣ Text is saved to Firestore with a timestamp.  
7️⃣ User can view all saved bills later using the **Show Saved Bills** option.

---

## 🚀 Installation & Setup

1️⃣ Clone this repository.  
2️⃣ Open in **Android Studio**.  
3️⃣ Add your Firebase configuration file (`google-services.json`) inside the `app` module.  
4️⃣ Sync and build the project.  
5️⃣ Run on a physical device or emulator (camera and storage permissions needed).

---

## ⚙️ Requirements

- Android Studio Arctic Fox or newer.
- Minimum Android SDK 21 (Lollipop).
- Firebase Firestore set up and enabled.
- Internet connection for cloud saving and fetching.

---

## 📸 Screenshots

<p align="left">
  <img src="https://github.com/user-attachments/assets/6fe8d9e4-483d-4672-ab59-33c7af3b3900" width="220"/>
  <img src="https://github.com/user-attachments/assets/9fbc8940-6647-4d4d-a83e-3b125898b002" width="220"/>
  <img src="https://github.com/user-attachments/assets/69365b31-8694-4371-a3de-b1ee81270a97" width="220"/>
  <img src="https://github.com/user-attachments/assets/e768ca8d-a764-41f5-ac3e-29734a78ffde" width="220"/>
   <img src="https://github.com/user-attachments/assets/327576c9-074a-4755-ac84-1cc606a9fdd6" width="220"/>
   <img src="https://github.com/user-attachments/assets/9a82c344-83c4-41ca-ac6f-612330c636d1" width="220"/>
  <img src="https://github.com/user-attachments/assets/750e8821-81d9-418b-905e-d8145b6158cd" width="220"/>
  <img src="https://github.com/user-attachments/assets/d187fc34-6ac9-4e7c-95fc-4719c7b54cc4" />
</p>

---

## 💬 Why This App?

✨ Helps people who struggle to read printed bills.  
✨ Supports **Telugu**, making it more helpful for local users.  
✨ Adjustable text size to improve accessibility.  
✨ Clean and user-friendly interface to encourage confidence.

---

## 👩‍💻 Developer

- **Veeranki Phani Sirisha**
- 📧 [veerankiphanisirisha@gmail.com](mailto:veerankiphanisirisha@gmail.com)


