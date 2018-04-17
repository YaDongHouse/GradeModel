package com.yadong.gradle;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @packInfo:com.yadong.gradle
 * @author: yadong.qiu
 * Created by 邱亚东
 * Date: 2018/1/12
 * Time: 18:01
 */

public class Book implements Parcelable {
    public String bookName;
    public int price;

    protected Book(Parcel in) {
        bookName = in.readString();
        price = in.readInt();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(bookName);
        dest.writeInt(price);
    }
}
