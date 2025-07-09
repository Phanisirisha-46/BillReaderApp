# 📄 Bill Reader App

## 💡 Problem Statement

Many people, especially senior citizens or those who are not comfortable reading printed bills, struggle to understand bill details clearly. This becomes a challenge in cases like electricity bills, shopping bills, hospital bills, etc.

The **Bill Reader App** aims to make it easy for anyone to scan a printed bill using their mobile camera or gallery image, extract the text (bill details), and read it aloud. Additionally, it saves scanned text to a cloud database for future reference.

---

## 🎯 Features

✅ Capture bill image using camera.  
✅ Pick bill image from gallery.  
✅ Use ML Kit OCR to extract text from image line by line (left to right) for accurate reading.  
✅ Read out the extracted text using Text-to-Speech.  
✅ Save bill text to Firebase Firestore.  

---

## 🛠️ Tech Stack Used

* **Android (Java)** — Main app framework.
* **Google ML Kit** — Text recognition (OCR) from images.
* **Firebase Firestore** — Cloud database to store bill texts with timestamps.
* **Android Text-to-Speech (TTS)** — Read extracted bill text aloud in English.

---

## 🏗️ Architecture & Flow

1️⃣ User opens app and sees two options: **Open Camera** or **Open Gallery**.  
2️⃣ User takes or selects an image.  
3️⃣ App uses ML Kit to scan text line by line from left to right.  
4️⃣ Extracted text is shown on screen and read aloud automatically in English.  
5️⃣ Extracted text is saved to Firestore with timestamp.  

---

## 🚀 Installation & Setup

1️⃣ Clone this repo.  
2️⃣ Open in **Android Studio**.  
3️⃣ Add your Firebase configuration (`google-services.json`) to the app module.  
4️⃣ Sync project and build.  
5️⃣ Run on emulator or physical device.  

---

## ⚙️ Requirements

* Android Studio Arctic Fox or newer.
* Minimum Android SDK 21 (Lollipop).
* Firebase project setup (Firestore enabled).
* Internet connection for Firestore saving.

---


## 📸 Screenshots

<p align="left">
  <img src="https://github.com/user-attachments/assets/d6c7a221-54b8-47dc-b8c3-98923fb3bdf2" width="220"/>
  <img src="https://github.com/user-attachments/assets/1feacd0b-3e45-4234-bf24-a9e2d66a6fa2" width="220"/>
  <img src="https://github.com/user-attachments/assets/4b39d17f-facf-4b1d-ad31-d0a00a9cc7ac" width="220"/>
  <img src="https://github.com/user-attachments/assets/a736ddad-393a-44fb-ba2d-7694036af644" width="220"/>
  <img src="https://github.com/user-attachments/assets/d187fc34-6ac9-4e7c-95fc-4719c7b54cc4" />
</p>














---

## 📬 Contact

* Developer: Veeranki Phani Sirisha
* Email: [veerankiphanisirisha@gmail.com](mailto:veerankiphanisirisha@gmail.com)

