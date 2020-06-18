package com.archi.architecture.server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class BookService extends Service {
    private static final String TAG_BIND = "BBBBIND";

    private List<Book> mBookList;

    public BookService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mBookList = new ArrayList<>();
        initData();
        Log.i(TAG_BIND, "onCreate");
    }

    private void initData() {
        Book book1 = new Book("活着");
        Book book2 = new Book("在人间");
        Book book3 = new Book("我的大学");
        Book book4 = new Book("童年");
        Book book5 = new Book("老兵");
        Book book6 = new Book("大秦帝国");
        mBookList.add(book1);
        mBookList.add(book2);
        mBookList.add(book3);
        mBookList.add(book4);
        mBookList.add(book5);
        mBookList.add(book6);
    }

    private final BookController.Stub stub = new BookController.Stub() {

        @Override
        public List<Book> getBookList() throws RemoteException {
            Log.i(TAG_BIND, "server send books size = " + mBookList.size());
            return mBookList;
        }

        @Override
        public void addBookInOut(Book book) throws RemoteException {
            Log.e(TAG_BIND, "server get book " + (book == null ? "lost" : book.getName()));
            if (book != null) {
                mBookList.add(book);
            }
        }

        @Override
        public void addBookIn(Book book) throws RemoteException {

        }

        @Override
        public void addBookOut(Book book) throws RemoteException {

        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG_BIND, "onBind");
        return stub;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
}
