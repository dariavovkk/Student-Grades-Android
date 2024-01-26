package com.example.project1;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class MainActivity extends AppCompatActivity {
    //Deklaracja pól widoku
    private EditText firstNameEditText, lastNameEditText, gradesEditText;
    private TextView errorMessageTextView;
    private Button submitButton;
    private Button AverageButton;
    // Stałe klucze dla zapisu stanu
    private static final String FIRST_NAME_KEY = "first_name";
    private static final String LAST_NAME_KEY = "last_name";
    private static final String GRADES_KEY = "grades";
    static final String AMOUNT_KEY = "";
    private static final String AVERAGE_BUTTON_KEY = "checkAverageButton";
    private static final String AVERAGE_KEY = "Average";
    private static final String BUTTON_AVERAGE_KEY = "button_average";
    private static final String BUTTON_AVERAGE_SHOW_KEY = "button_average_show";
    private static final String ERROR_MESSAGE_KEY = "error_message";
    //Zmienne stanu
    private boolean checkImie = false;
    private boolean checkNazwisko = false;
    private boolean checkGrade = false;
    private boolean checkAverageButton = false;
    private Double Average = 0.0;


    //Obiekt do obsługi rezultatu z drugiej aktywności
    private ActivityResultLauncher<Intent> mActivityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Inicjalizacja pól widoku
        firstNameEditText = findViewById(R.id.firstName);
        lastNameEditText = findViewById(R.id.lastName);
        gradesEditText = findViewById(R.id.grades);
        errorMessageTextView = findViewById(R.id.error_message_textview);
        submitButton = (Button) findViewById(R.id.submit);
        submitButton.setVisibility(View.INVISIBLE);
        AverageButton = (Button) findViewById(R.id.AverageButton);
        AverageButton.setVisibility(View.INVISIBLE);
        // Inicjalizacja obiektu do obsługi rezultatu
        mActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(
                            ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK) {
                            System.out.println(checkImie);
                            System.out.println(checkNazwisko);
                            System.out.println(checkGrade);
                            Intent resultIntent = result.getData();
                            Average = Double.parseDouble(resultIntent.getStringExtra(GradesActivity.Average_Key));
                            Toast.makeText(MainActivity.this, Average.toString(), Toast.LENGTH_LONG).show();
                            checkAverageButton = true;
                            CheckResAverageButton();
                        }
                    }
                }
        );
        //Ustawienie nasłuchiwania kliknięcia przycisku "submit"
        submitButton.setOnClickListener(v -> startSecondActivity());

        AverageButton.setOnClickListener(v -> {
            String buttonAverageShow = AverageButton.getTag().toString();
            Toast.makeText(MainActivity.this, buttonAverageShow, Toast.LENGTH_LONG).show();
        });

        //Nasłuchiwanie zmian w polu firstNameEditText
        firstNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (firstNameEditText.getText().toString().isEmpty()) {
                    checkImie = false;
                    return;
                }
                checkImie = true;
            }

            @Override
            public void afterTextChanged(Editable s) {
                validateFirstName();
            }
        });

        //Dodanie obsługi zdarzeń dla pól edycyjnych
        firstNameEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                validateFirstName();
            }
        });
        //Nasłuchiwanie zmian w polu lastNameEditText
        lastNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (lastNameEditText.getText().toString().isEmpty()) {
                    checkNazwisko = false;
                    return;
                }
                checkNazwisko = true;
            }

            @Override
            public void afterTextChanged(Editable s) {
                validateLastName();
            }
        });
        //Dodanie obsługi zdarzeń dla pól edycyjnych
        lastNameEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                validateLastName();
            }
        });

        //Nasłuchiwanie zmian w polu gradesEditText
        gradesEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (gradesEditText.getText().toString().isEmpty()) {
                    checkGrade = false;
                    return;
                }
                int LiczbaOcen = Integer.parseInt(gradesEditText.getText().toString());
                if (!(LiczbaOcen >= 5 && LiczbaOcen <= 15)) {
                    gradesEditText.setError("Liczba ocen [5-15");
                    checkGrade = false;
                    return;
                }
                checkGrade = true;
            }

            @Override
            public void afterTextChanged(Editable s) {
                validateGrades();
            }
        });
        //Dodanie obsługi zdarzeń dla pól edycyjnych
        gradesEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                validateGrades();
            }
        });
    }

    //Zapis stanu aktywności
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        //Zapis wartości pól tekstowych, komunikatu o błędzie i innych zmiennych
        outState.putString(FIRST_NAME_KEY, firstNameEditText.getText().toString());
        outState.putString(LAST_NAME_KEY, lastNameEditText.getText().toString());
        outState.putString(GRADES_KEY, gradesEditText.getText().toString());
        outState.putString(ERROR_MESSAGE_KEY, errorMessageTextView.getText().toString());
        outState.putBoolean(AVERAGE_BUTTON_KEY, (boolean) checkAverageButton);
        outState.putDouble(AVERAGE_KEY, Average);
        outState.putString(BUTTON_AVERAGE_KEY, AverageButton.getText().toString());
        outState.putString(BUTTON_AVERAGE_SHOW_KEY, AverageButton.getText().toString());
        System.out.println(checkNazwisko);
    }

    //Przywrót stanu aktywności
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            //Przywrót zapisanych wartości pól, komunikatu o błędzie i innych zmiennych
            firstNameEditText.setText(savedInstanceState.getString(FIRST_NAME_KEY, ""));
            lastNameEditText.setText(savedInstanceState.getString(LAST_NAME_KEY, ""));
            gradesEditText.setText(savedInstanceState.getString(GRADES_KEY, ""));
            errorMessageTextView.setText(savedInstanceState.getString(ERROR_MESSAGE_KEY, ""));
            AverageButton.setEnabled(isFormValid());
            Average = savedInstanceState.getDouble(AVERAGE_KEY);
            String buttonAverage = savedInstanceState.getString(BUTTON_AVERAGE_KEY);
            String buttonAverageShow = savedInstanceState.getString(BUTTON_AVERAGE_SHOW_KEY);
            AverageButton.setText(buttonAverage);
            AverageButton.setTag(buttonAverageShow);
            //AverageButton.setOnClickListener(v -> Toast.makeText(MainActivity.this, buttonAverageShow, Toast.LENGTH_LONG).show());
        }
        CheckEditText();
        CheckResAverageButton();
    }

    //Rozpocznij drugą aktywność
    private void startSecondActivity() {
        Intent intent = new Intent(this, GradesActivity.class);
        intent.putExtra(AMOUNT_KEY, gradesEditText.getText().toString());
        mActivityResultLauncher.launch(intent);
    }

        //Metoda sprawdzająca poprawność pola Imię
        private void validateFirstName () {
            String firstName = firstNameEditText.getText().toString().trim();
            if (TextUtils.isEmpty(firstName)) {
                firstNameEditText.setError(getString(R.string.first_name_error));
                submitButton.setVisibility(View.INVISIBLE);
                checkImie = false;
            } else {
                firstNameEditText.setError(null);
                checkImie = true;
                CheckEditText();
            }
        }

        //Metoda sprawdzająca poprawność pola Nazwisko
        private void validateLastName () {
            String lastName = lastNameEditText.getText().toString().trim();
            if (TextUtils.isEmpty(lastName)) {
                lastNameEditText.setError(getString(R.string.last_name_error));
                submitButton.setVisibility(View.INVISIBLE);
                checkNazwisko = false;
            } else {
                lastNameEditText.setError(null);
                checkNazwisko = true;
                CheckEditText();
            }
        }

        //Metoda sprawdzająca poprawność pola Liczba ocen
        private void validateGrades () {
            String gradesString = gradesEditText.getText().toString().trim();
            if (TextUtils.isEmpty(gradesString)) {
                gradesEditText.setError(getString(R.string.error_empty_field, getString(R.string.grades_field)));
                submitButton.setVisibility(View.INVISIBLE);
                checkGrade = false;
            }
            int grades = Integer.parseInt(gradesString);
            if (grades < 5 || grades > 15) {
                gradesEditText.setError(getString(R.string.error_invalid_grades));
                submitButton.setVisibility(View.INVISIBLE);
                checkGrade = false;
            } else {
                gradesEditText.setError(null);
                checkGrade = true;
                CheckEditText();
            }
        }
        //Metoda sprawdzająca widoczność przycisku submit
        private void CheckEditText() {
            if (checkImie && checkNazwisko && checkGrade) {
                submitButton.setVisibility(View.VISIBLE);
            } else {
                submitButton.setVisibility(View.INVISIBLE);
            }
        }
    //Metoda sprawdzająca widoczność przycisku AverageButton
        protected void CheckResAverageButton() {
            String ButtonAverage;
            String ButtonAverageShow;
            if (!checkAverageButton) {
                AverageButton.setVisibility(View.INVISIBLE);
                return;
            }
            AverageButton.setVisibility(View.VISIBLE);
            if (Average >= 3) {
                ButtonAverage = "Super!";
                ButtonAverageShow = "Gratulacje! Otrzymujesz zaliczenie!";
            } else {
                ButtonAverage = "Tym razem mi nie poszło";
                ButtonAverageShow = "Wysyłam podanie o zaliczenie warunkowe";
            }
            AverageButton.setText(ButtonAverage);
            String endButtonAverageShow = ButtonAverageShow;
            AverageButton.setOnClickListener(v -> Toast.makeText(MainActivity.this, endButtonAverageShow, Toast.LENGTH_LONG).show());
        }

        //Metoda sprawdzająca, czy wszystkie pola formularza są poprawne
        private boolean isFormValid () {
            return !TextUtils.isEmpty(firstNameEditText.getText().toString().trim()) &&
                    !TextUtils.isEmpty(lastNameEditText.getText().toString().trim()) &&
                    !TextUtils.isEmpty(gradesEditText.getText().toString().trim()) &&
                    Integer.parseInt(gradesEditText.getText().toString().trim()) >= 5 &&
                    Integer.parseInt(gradesEditText.getText().toString().trim()) <= 15;
        }
    }
