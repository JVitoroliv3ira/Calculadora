package com.example.calculadora.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.calculadora.R;

import java.util.ArrayList;
import java.util.List;

public class Calculadora extends Fragment {

    private final List<String> pilha = new ArrayList<>();

    public Calculadora() {
    }

    public static Calculadora newInstance() {
        return new Calculadora();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calculadora, container, false);
        final Button botao1 = view.findViewById(R.id.button_1);
        final Button botao2 = view.findViewById(R.id.button_2);
        final Button botao3 = view.findViewById(R.id.button_3);
        final Button botao4 = view.findViewById(R.id.button_4);
        final Button botao5 = view.findViewById(R.id.button_5);
        final Button botao6 = view.findViewById(R.id.button_6);
        final Button botao7 = view.findViewById(R.id.button_7);
        final Button botao8 = view.findViewById(R.id.button_8);
        final Button botao9 = view.findViewById(R.id.button_9);
        final Button botao0 = view.findViewById(R.id.button_0);
        final Button botao10 = view.findViewById(R.id.button_10);
        final Button botao11 = view.findViewById(R.id.button_11);
        final Button botao12 = view.findViewById(R.id.button_12);
        final Button botao13 = view.findViewById(R.id.button_13);
        final Button botao14 = view.findViewById(R.id.button_14);
        final TextView display = view.findViewById(R.id.text_view_display);

        botao1.setOnClickListener(v -> this.clicar("1", display));
        botao2.setOnClickListener(v -> this.clicar("2", display));
        botao3.setOnClickListener(v -> this.clicar("3", display));
        botao4.setOnClickListener(v -> this.clicar("4", display));
        botao5.setOnClickListener(v -> this.clicar("5", display));
        botao6.setOnClickListener(v -> this.clicar("6", display));
        botao7.setOnClickListener(v -> this.clicar("7", display));
        botao8.setOnClickListener(v -> this.clicar("8", display));
        botao9.setOnClickListener(v -> this.clicar("9", display));
        botao0.setOnClickListener(v -> this.clicar("0", display));
        botao10.setOnClickListener(v -> this.clicar("+", display));
        botao11.setOnClickListener(v -> this.clicar("-", display));
        botao12.setOnClickListener(v -> this.clicar("*", display));
        botao13.setOnClickListener(v -> this.clicar("/", display));
        botao14.setOnClickListener(v -> {
            this.removerUltimoValorDaPilha();
            this.exibirPilha(display);
        });

        return view;
    }

    private void clicar(String valor, TextView display) {
        this.adicionarValorNaPilha(valor);
        this.exibirPilha(display);
    }

    private void adicionarValorNaPilha(String valor) {
        this.pilha.add(valor);
    }

    private void removerUltimoValorDaPilha() {
        if (!this.pilha.isEmpty()) {
            int posicao = this.pilha.size() - 1;
            this.pilha.remove(posicao);
        }
    }

    private void exibirPilha(TextView display) {
        String concatenado = String.join("", this.pilha);
        display.setText(concatenado);
    }
}