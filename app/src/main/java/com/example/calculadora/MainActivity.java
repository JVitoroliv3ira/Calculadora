package com.example.calculadora;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.calculadora.enums.SelectedFragment;
import com.example.calculadora.fragments.Calculadora;
import com.example.calculadora.fragments.CalculadoraNotas;

public class MainActivity extends AppCompatActivity {

    private SelectedFragment selectedFragment = SelectedFragment.CALCULADORA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button botaoCalculadora = findViewById(R.id.btn_calculadora);
        final Button botaoCalculadoraNotas = findViewById(R.id.btn_calculadora_notas);
        this.atualizarFragmentoExibido(null, botaoCalculadora, botaoCalculadoraNotas);

        botaoCalculadora.setOnClickListener(view -> {
            this.selecionarCalculadora();
            this.atualizarFragmentoExibido(view, botaoCalculadora, botaoCalculadoraNotas);
        });
        botaoCalculadoraNotas.setOnClickListener(view -> {
            this.selecionarCalculadoraNotas();
            this.atualizarFragmentoExibido(view, botaoCalculadora, botaoCalculadoraNotas);
        });
    }

    private void selecionarCalculadora() {
        this.selectedFragment = SelectedFragment.CALCULADORA;
    }

    private void selecionarCalculadoraNotas() {
        this.selectedFragment = SelectedFragment.CALCULADORA_NOTAS;
    }

    private void atualizarFragmentoExibido(View view, Button botaoCalculadora, Button botaoCalculadoraNotas) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (Boolean.TRUE.equals(SelectedFragment.CALCULADORA.equals(this.selectedFragment))) {
            transaction.replace(R.id.layout, Calculadora.newInstance());
            botaoCalculadora.setText("CALCULADORA *");
            botaoCalculadoraNotas.setText("NOTAS");
        } else {
            transaction.replace(R.id.layout, CalculadoraNotas.newInstance());
            botaoCalculadora.setText("CALCULADORA");
            botaoCalculadoraNotas.setText("NOTAS *");
        }
        transaction.commit();
    }
}