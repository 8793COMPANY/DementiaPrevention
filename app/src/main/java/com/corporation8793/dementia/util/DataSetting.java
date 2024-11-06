package com.corporation8793.dementia.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.corporation8793.dementia.data.AppDatabase;
import com.corporation8793.dementia.data.Chat;
import com.corporation8793.dementia.data.ChatDao;
import com.corporation8793.dementia.data.DiagnoseList;
import com.corporation8793.dementia.data.DiagnoseListDao;
import com.corporation8793.dementia.data.Question;
import com.corporation8793.dementia.data.QuestionDao;
import com.corporation8793.dementia.data.User;
import com.corporation8793.dementia.data.UserDao;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class DataSetting {

    Context context;

    InputStream inputStream;
    Workbook workbook;

    AppDatabase appDatabase = null;

    public ChatDao chatDao;
    public UserDao userDao;
    public QuestionDao questionDao;
    public DiagnoseListDao diagnoseListDao;

    List<User> userList = new ArrayList<>();
    List<Question> questionList = new ArrayList<>();
    List<DiagnoseList> diagnoseLists = new ArrayList<>();

    public DataSetting(Context context) {
        this.context = context;

        appDatabase = AppDatabase.getInstance(this.context);

        chatDao = appDatabase.chatDao();
        userDao = appDatabase.userDao();
        questionDao = appDatabase.questionDao();
        diagnoseListDao = appDatabase.diagnoseListDao();
    }

    @SuppressLint("StaticFieldLeak")
    private static DataSetting settingManager = null;

    // 싱글톤 패턴 구현
    public static DataSetting getInstance(Context context) {
        if (settingManager == null) {
            settingManager = new DataSetting(context);
        }

        return settingManager;
    }

    public boolean dataCheck() {
        boolean check = true;

        // 데이터가 없을 경우
        if (questionDao.findAll().isEmpty()) {
            questionDao.deleteAll();
            readQuestionList();
        }
//        questionDao.deleteAll();
//        readQuestionList();

        return check;
    }

    public List<User> getUserList() {
        userList = userDao.getAll();

        return userList;
    }

    public List<Question> getQuestionList() {
        questionList = questionDao.findAll();

        return questionList;
    }

    public List<DiagnoseList> getDiagnoseLists() {
        diagnoseLists = diagnoseListDao.getAll();

        return diagnoseLists;
    }

    public void insert_chat_data(Chat chat) {
        chatDao.insert(chat);
    }

    public void insert_user_data(User user) {
        userDao.insert(user);
    }
    public void update_user_data(int id, User user) {
//        userDao.update(user);
        userDao.updateUserById(id, user.name, user.region, user.ageRange, user.birthday);
        Application.setUserData(getUserList().get(0));
    }

    public void insert_question_data(Question question) {
        questionDao.insert(question);
    }

    public void insert_diagnose_list_data(DiagnoseList diagnoseList) {
        diagnoseListDao.insert(diagnoseList);
    }

    public void readQuestionList() {
        Log.e("check in", "readQuestionList");

        try {
            inputStream = context.getResources().getAssets().open("question_list.xls");
            workbook = Workbook.getWorkbook(inputStream);

            if (workbook != null) {
                Log.e("check in","sheet");

                Sheet sheet = workbook.getSheet(0);

                if (sheet != null) {
                    int colTotal = sheet.getColumns();
                    int rowTotal = sheet.getRows();

                    Log.e("check in","colTotal : " + colTotal);
                    Log.e("check in","rowTotal : " + rowTotal);

                    int rowIndexStart = 3;

                    for (int row = rowIndexStart; row < rowTotal; row++) {

                        Log.e("check in", sheet.getCell(1, row).getContents());
                        Log.e("check in", sheet.getCell(2, row).getContents());
                        Log.e("check in", sheet.getCell(3, row).getContents());
                        Log.e("check in", sheet.getCell(4, row).getContents());
                        Log.e("check in", sheet.getCell(5, row).getContents());
                        Log.e("check in", sheet.getCell(6, row).getContents());
                        Log.e("check in", sheet.getCell(7, row).getContents());

                        Question question = new Question(sheet.getCell(1, row).getContents(), sheet.getCell(2, row).getContents(),
                                sheet.getCell(3, row).getContents(), sheet.getCell(4, row).getContents(),
                                sheet.getCell(5, row).getContents(), sheet.getCell(6, row).getContents(), sheet.getCell(7, row).getContents());

                        insert_question_data(question);
                    }
                }
            }

        } catch (IOException | BiffException ioException) {
            ioException.getMessage();
            Log.e("check in", ioException.toString());
        }

    }
}
