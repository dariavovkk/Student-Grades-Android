package com.example.project1;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Grade implements Parcelable {
    private String przedmiot;
    private int grade;

    public Grade(String przedmiot, int grade) {
        this.przedmiot = przedmiot;
        this.grade = grade;
    }
    protected Grade(Parcel source){
        //Odczyt wartości z obiektu Parcel
        przedmiot = source.readString();
        grade = source.readInt();
    }

    //Implementacja interfejsu Parcelable
    public static final Creator<Grade> CREATOR = new Creator<Grade>() {
        @Override
        //Tworzenie nowej instancji klasy Grade na podstawie obiektu Parcel
        public Grade createFromParcel(Parcel source) {
            return new Grade(source);
        }

        //Tworzenie tablicy obiektów klasy Garde o podanym rozmiarze
        @Override
        public Grade[] newArray(int size) {
            return new Grade[size];
        }
    };

    public String getPrzedmiot() {
        return przedmiot;
    }

    public int getGrade() {
        return grade;
    }

    public void setPrzedmiot(String przedmiot) {
        this.przedmiot = przedmiot;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    //Przesłonięcie metody toString
    @Override
    public String toString(){
        return "Grade{" + "przedmiot='" + przedmiot + '\'' + ", grade=" + grade + '}';
    }
    //Implementacja interfejsu Parcelable
    @Override
    public int describeContents(){
        return 0;
    }
    //Zapis wartości do obiektu Parcel
    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i){
        parcel.writeString(przedmiot);
        parcel.writeInt(grade);
    }
}
