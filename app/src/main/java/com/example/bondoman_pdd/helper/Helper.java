package com.example.bondoman_pdd.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;

public class Helper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "pdd.db";

    public Helper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS `questions` (\n" +
                "  `nim` INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "  `question` TEXT,\n" +
                "  `answer1` TEXT,\n" +
                "  `answer2` TEXT,\n" +
                "  `answer3` TEXT,\n" +
                "  `answer4` TEXT,\n" +
                "  `right_answer` INTEGER\n" +
                ");");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS `results` (\n" +
                "  `id` INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "  `right_answers` INTEGER,\n" +
                "  `wrong_answers` INTEGER,\n" +
                "  `date` TEXT\n" +
                ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS questions");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS results");
        onCreate(sqLiteDatabase);
    }

    // Mengambil NIM dari

//    public ArrayList<HashMap<String, String>> getQuestions() {
//    }
}