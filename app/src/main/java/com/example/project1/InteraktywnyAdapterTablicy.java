package com.example.project1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class InteraktywnyAdapterTablicy extends RecyclerView.Adapter<InteraktywnyAdapterTablicy.OcenyViewHolder> {
    public ArrayList<Grade> getmLista(){return mLista;}
    private ArrayList<Grade> mLista;
    private LayoutInflater mInflater; //Obiekt do tworzenia widoków

    public InteraktywnyAdapterTablicy(Context context, ArrayList<Grade> listaOcen) {
        this.mLista = listaOcen;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public OcenyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.oceny, parent, false);//Tworzenie widoku dla pojedynczego wiersza
        return new OcenyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull OcenyViewHolder ocenyViewHolder, int wiersz) {
        int ocena = mLista.get(ocenyViewHolder.getAdapterPosition()).getGrade();
        switch(ocena) {
            case 2:
                ocenyViewHolder.rgOceny.check(R.id.rButton2);
                System.out.println("Ocena: 2");
                break;
            case 3:
                ocenyViewHolder.rgOceny.check(R.id.rButton3);
                System.out.println("Ocena: 3");
                break;
            case 4:
                ocenyViewHolder.rgOceny.check(R.id.rButton4);
                System.out.println("Ocena: 4");
                break;
            case 5:
                ocenyViewHolder.rgOceny.check(R.id.rButton5);
                System.out.println("Ocena: 5");
                break;
        }
        ocenyViewHolder.tvPrzedmiot.setText(mLista.get(wiersz).getPrzedmiot());
        mLista.get(wiersz).setGrade(ocenyViewHolder.getGrade());
    }

    @Override
    public int getItemCount() {
        return mLista.size();
    }
    public float getAverage(int iloscOcen) {
        int ilosc = 0;
        for (int i = 0; i<iloscOcen; i++) {
            ilosc += mLista.get(i).getGrade();
        }
        return (float)ilosc/(float)iloscOcen;
    }

    public class OcenyViewHolder extends RecyclerView.ViewHolder implements RadioGroup.OnCheckedChangeListener {
        public TextView tvPrzedmiot;
        public RadioGroup rgOceny;
        int grade;

        public OcenyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPrzedmiot = itemView.findViewById(R.id.tvPrzedmiot);
            rgOceny = itemView.findViewById(R.id.radioGroup);
            rgOceny.setOnCheckedChangeListener(this);
        }
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            RadioButton checkedRB = (RadioButton)group.findViewById(checkedId);
            grade = Integer.parseInt(checkedRB.getText().toString());
            mLista.get(this.getAdapterPosition()).setGrade(grade);
        }
        public int getGrade() {
            return grade;
        }
    }
}

