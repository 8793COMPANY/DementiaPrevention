package com.corporation8793.dementia.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Chat.class, User.class, Question.class, DiagnoseList.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {

    public abstract ChatDao chatDao();
    public abstract UserDao userDao();
    public abstract QuestionDao questionDao();
    public abstract DiagnoseListDao diagnoseListDao();

    private static volatile AppDatabase INSTANCE = null;

    // 싱글톤 패턴 구현
    public static AppDatabase getInstance(Context context) {

        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "dementia_database")
//                        .fallbackToDestructiveMigration() //스키마 버전 변경 가능
                        .allowMainThreadQueries() // 메인 스레드에서 DB에 IO를 가능하게 함
//                        나중에 데이터베이스 변경 사항 발생되면 마이그레이션을 추가해서 설정하기
                        .addMigrations(MIGRATION_1_TO_2/*, MIGRATION_2_TO_3*/)
                        .build();
            }
        }

        return INSTANCE;
    }

    static final Migration MIGRATION_1_TO_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE IF NOT EXISTS `DiagnoseList` (`did` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                    + "`date` TEXT, " + "`resultText` TEXT, " + "`resultScore` INTEGER NOT NULL)");
        }
    };
}
