package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // задание полей
    float TelescopePrice = 14_000; // стоимость телескопа
    int account = 1000; // счёт пользователя
    float stipend = 2500; // стипендия
    int percentFree = 100; // доля стипендии на любые траты
    float percentBank = 5; // годовая процентная ставка на счету
    float[] monthlyAccount = new float[120]; // создание массива ежемесячных платежей на 10 лет

    // метод подсчёта стоимости телескопа с учётом первоначального взноса
    private float residualValueTelescope() {
        return TelescopePrice - account; // возврат подсчитанного значения
    }

    // метод подсчёта ежемесячных трат на телескоп (стипендия, процент своб.трат)
    public float monthlySavings(float amount, int percent) {
        return (amount*percent)/100;
    }

    // метод подсчёта времени накопления (сумма на счету, сумма платежа, годовой процент)
    // и заполнение массива monthlyPayments[] ежемесячным состоянием счета
    public int countMonth(float total, float monthlySavings, float percentBankYear) {

        float percentBankMonth = percentBankYear / 12; // подсчёт ежемесячного процента банка
        int count = 0; // счётчик месяцев накопления

        // алгоритм расчёта накопления
        while (total <= TelescopePrice) {
            count++; // добавление нового месяца накоплений
            total = (total + (total*percentBankMonth)/100)+monthlySavings; // вычисление накоплений с учётом процента
            // заполнение массива ежемесячными накоплениями
            if(total < TelescopePrice) { // если сумма меньше цены телескопа, то
                monthlyAccount[count-1] = total; // состояние счета
            } else { // иначе
                monthlyAccount[count-1] = TelescopePrice; // состояние счета
            }
        }

        return count;
    }

    // создание дополнительных полей для вывода на экран полученных значений
    private TextView countOut; // поле вывода количества месяцев накопления
    private TextView manyMonthOut; // поле выписки по ежемесячному состоянию счета

    // вывод на экран полученных значений
    @Override
    protected void onCreate(Bundle savedInstanceState) { // создание жизненного цикла активности
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);// присваивание жизненному циклу активити представления activity_main

        // присваивание переменным активити элементов представления activity_main
        countOut = findViewById(R.id.countOut); // вывод информации количества месяцев накоплений
        manyMonthOut = findViewById(R.id.manyMonthOut); // вывод информации по ежемесячному состоянию счета

        // запонение экрана
        // 1) вывод количества месяцев накоплений
        countOut.setText("Накопление будет длиться " + countMonth(account, monthlySavings(stipend, percentFree),percentBank) + " месяцев");
        // 2) подготовка выписки по состоянию счета
        String monthlyPaymentsList = "";
        for(float list : monthlyAccount) {
            if (list > 0) {
                monthlyPaymentsList = monthlyPaymentsList + Float.toString(list) + " монет " ;
            } else {
                break;
            }
        }
        // 3) вывод выписки ежемесячных накоплений
        manyMonthOut.setText("Первоначальный взнос " + account + " монет, ежемесячное состояние счета: " + monthlyPaymentsList);

    }
}