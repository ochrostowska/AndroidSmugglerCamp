package com.dotnet.smugglercamp.database;

import android.util.Log;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DatabaseHelper {

    private static final String BASE_URL = "http://smugglercamp.azurewebsites.net/";
    public static final String TAG = DatabaseHelper.class.getSimpleName();
    private ItemsAPI api;

    private static DatabaseHelper ourInstance;

    // Metoda zwracająca instancję klasy DatabaseHelper
    public static DatabaseHelper getInstance() {
        // Jeśli instancja nie istnieje -> tworzymy nową
        if(ourInstance==null) ourInstance = new DatabaseHelper();
        return ourInstance;
    }

    // Konstruktor klasy
    public DatabaseHelper() {
    }

    private void initializeRetrofit() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(ItemsAPI.class);
    }

    public void sendData(int id, String codename, String name, int quantity) {

        initializeRetrofit();
        Call<ResponseBody> call = api.insertUser(id, codename, name, quantity);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String output = "";
                try {
                    output = response.body().string();
                    Log.d(TAG, output);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "ERROR !!! " + t.getMessage());
            }
        });
    }
}

/* OBJAŚNIENIA DO METOD

private void initializeRetrofit() {

        Retrofit jest biblioteką adaptującą interfejs Javy do wykonywania
        zapytań HTTP. Poprzez adnotacje(przypisy) do deklarowanych metod
        (w klasie ItemsAPI) definiujemy jak wykonywane jest zapytanie.
        Tworzymy obiekt klasy Retrofit i poprzez klasę Builder
        nadajemy mu pewne "cechy", które musi posiadać aby przetwarzać
        nasze zapytania.

        Retrofit retrofit = new Retrofit.Builder()

                Bazowy URL naszego serwera.

                .baseUrl(ROOT_URL)


                Odpowiedź jaką dostajemy ma postać ciągu bajtów,
                aby móc właściwie odczytać dane musimy przeprowadzić
                proces serializacji/deserializacji danych.
                Jednak nie musimy tego robić sami, ponieważ istnieją
                do tego cudowne biblioteki. To co robi ta linijka to
                zwyczajnie dodaje konwerter do typu danych jaki potrzebujemy.
                W naszym przypadku jest to Gson, czyli konwerter
                do serializacji i deserializacji danych zapisanych
                w formacie JSON.

                .addConverterFactory(GsonConverterFactory.create())

                Kończymy ulepszanie naszego Retrofita

                .build();

        Tworzymy API = interfejs programistyczny aplikacji –
        - czyli zestaw reguł i ich opisów, w jaki programy komunikują
        się między sobą.
        W naszej klasie ItemsAPI mamy 2 metody, które wykorzystujemy
        modyfikowanie danych. Definiuja one w jaki sposob aplikacja
        komunikuje się z baza danych.

        api = retrofit.create(ItemsAPI.class);
    }

public void sendData(int id, String codename, String name, int quantity) {

        initializeRetrofit();

        Stworzenie obiektu Call biblioteki Retrofit która wysyła zapytanie
        zdefiniowane z metodzie insertUser klay ItemsAPI
        do webserwera i zwraca odpowiedź (Callback).

        Call<ResponseBody> call = api.insertUser(id, codename, name, quantity);

        Wywołujemy metodę enqueue(), która wyśle zapytanie asynchronicznie.
        W przypadku gdy wystąpi błąd - gdy Call nie moze wykonać zapytania,
        bądź uzyskać odpowiedzi - moze zwrócić Exception.
        Jeżeli wszystko pójdzie "zgodnie z planem" uzystamy
        odpowiedz(response) o typie <T>.
        Callback jest interfejsem posiadającym dwie metody.
        W odpowiedzi na każdy Call wykona się tylko jedna z nich.


        call.enqueue(new Callback<ResponseBody>() {

            Wywoływana gdy otrzymamy poprawną odpowiedź o typie
            zdefiniowanym jako <T>.

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String output = "";
                try {
                    output = response.body().string();
                    Log.d(TAG, output);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            Wywoływana gdy nastąpi wyjątek. Throwable t jest zmienną
            zawierającą informacje o tym, jakiego typu wyjątek nastąpił.
            Może to być problem z komunikacją z serwerem lub choćby
            problem z przetwarzaniem odpowiedzi na zadany typ <T>.

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "ERROR !!! " + t.getMessage());
            }
        });
    }


 */