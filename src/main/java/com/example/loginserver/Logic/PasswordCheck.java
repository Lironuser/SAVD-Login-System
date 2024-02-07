package com.example.loginserver.Logic;

import com.example.loginserver.Errors.PasswordError;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public class PasswordCheck {
    private static PasswordError checkLength(String password){
        if(password.length()<11 || password.length()>18){
            return PasswordError.LENGTH_ERROR;
        }
        return PasswordError.GOOD;
    }
    private static PasswordError checkChars(String password){
        if(!password.matches(".*[A-Z]*.")){
            return PasswordError.NO_BIG_CHAR;
        }
        if(!password.matches(".*[a-z]*.")){
            return PasswordError.NO_SMALL_CHAR;
        }
        if(!password.matches(".*[0-9]*.")){
            return PasswordError.NO_NUMBER;
        }
        if (!password.matches(".*[!]*.") || !password.matches(".*[_]*.")){
            return PasswordError.NO_SPECIAL_CHAR;
        }
        return PasswordError.GOOD;
    }
    public static PasswordError checkPassValid(String password){
        PasswordError e;
        e=checkLength(password);
        if(e!= PasswordError.GOOD){
            return e;
        }
        e=checkChars(password);
        return e;
    }



        // פונקציה לצפנת הסיסמה באמצעות האלגוריתם SHA-256
        public static String hashPassword(String password) {
            try {
                MessageDigest digest = MessageDigest.getInstance("SHA-256"); // יצירת אובייקט לצורך הצפנת הסיסמה
                byte[] hash = digest.digest(password.getBytes()); // הצפנת הסיסמה והשמת התוצאה במערך של bytes

                StringBuilder hexString = new StringBuilder();
                for (byte b : hash) {
                    String hex = Integer.toHexString(0xff & b); // המרת כל byte לתו הקורספונדנטי בייחודי
                    if (hex.length() == 1) {
                        hexString.append('0'); // הוספת אפס במידה והתו הקורספונדנטי נמצא בתחום 0-15 (שלא יופיע כתו בודד)
                    }
                    hexString.append(hex); // הוספת התו למחרוזת הסופית
                }
                return hexString.toString(); // החזרת המחרוזת הסופית - הגיבוב
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                return null;
            }
        }

        // פונקציה שלא ניתן להפוך את הגיבוב חזרה לסיסמה המקורית
        public static Boolean decryptHash(String hashedPassword) {
            // מכיוון ש-SHA-256 הוא פונקציית האש, אין אפשרות ישירה לפענוח אותו
            // פונקציית האש מייצרת גיבוב בלתי הפיך
            // לכן, לא ניתן לשחזר את הסיסמה המקורית מהגיבוב
            // אפשר לבצע רק חיפוש על סיסמאות מועמדות על ידי יצירת גיבוב חד
            return false; // הודעת שגיאה/התראה
        }
    }


