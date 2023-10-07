package com.example.calculadora.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.calculadora.R;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Stack;

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
        final Button botao15 = view.findViewById(R.id.button_15);
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
        botao15.setOnClickListener(v -> {
            this.calcular(display);
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
        if (Boolean.TRUE.equals(!this.pilha.isEmpty())) {
            int posicao = this.pilha.size() - 1;
            this.pilha.remove(posicao);
        }
    }

    private void calcular(TextView display) {
        Stack<Float> numeros = new Stack<>();
        Stack<String> operadores = new Stack<>();
        List<String> expressao = this.concatenarPilha();

        if (Boolean.TRUE.equals(!expressao.isEmpty())) {
            for (String token : expressao) {
                if (Boolean.TRUE.equals(this.isNumero(token))) {
                    numeros.push(Float.parseFloat(token));
                } else {
                    while (!operadores.isEmpty() && this.precedenciaOperacao(token) <= this.precedenciaOperacao(operadores.peek())) {
                        this.realizarOperacao(numeros, operadores);
                    }
                    operadores.push(token);
                }
            }

            while (!operadores.isEmpty()) {
                realizarOperacao(numeros, operadores);
            }

            this.exibirResultado(numeros.pop(), display);
        }
    }

    private List<String> concatenarPilha() {
        List<String> pilhaConcatenada = new ArrayList<>();
        StringBuilder valorAtual = new StringBuilder();

        if (Boolean.TRUE.equals(!this.pilha.isEmpty())) {
            for (int index = 0; index < this.pilha.size(); index++) {
                String token = this.pilha.get(index);
                if (Boolean.TRUE.equals(this.isNumero(token))) {
                    valorAtual.append(token);
                }
                if (Boolean.TRUE.equals(this.isNumero(token) && index == this.pilha.size() - 1)) {
                    pilhaConcatenada.add(valorAtual.toString());
                    valorAtual.setLength(0);
                }
                if (Boolean.TRUE.equals(!this.isNumero(token))) {
                    if (Boolean.TRUE.equals(",".equals(token) || ".".equals(token) || ("-".equals(token) && index == 0))) {
                        valorAtual.append(token);
                    } else {
                        pilhaConcatenada.add(valorAtual.toString());
                        pilhaConcatenada.add(token);
                        valorAtual.setLength(0);
                    }
                }
            }
        }

        return pilhaConcatenada;
    }

    private Boolean isNumero(String valor) {
        try {
            Float.parseFloat(valor);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    private Integer precedenciaOperacao(String operacao) {
        if (Boolean.TRUE.equals("+".equals(operacao) || "-".equals(operacao))) {
            return 1;
        }
        return 2;
    }

    private void realizarOperacao(Stack<Float> numeros, Stack<String> operadores) {
        float b = numeros.pop();
        float a = numeros.pop();
        String operador = operadores.pop();
        float resultado = 0;

        switch (operador) {
            case "+":
                resultado = a + b;
                break;
            case "-":
                resultado = a - b;
                break;
            case "*":
                resultado = a * b;
                break;
            case "/":
                if (b != 0) {
                    resultado = a / b;
                } else {
                    this.pilha.clear();
                    Toast.makeText(getContext(), "Não é possível realizar divisão por zero", Toast.LENGTH_LONG).show();
                }
                break;
        }

        numeros.push(resultado);
    }

    private void exibirPilha(TextView display) {
        String concatenado = String.join("", this.pilha);
        display.setText(concatenado);
    }

    private void exibirResultado(Float resultado, TextView display) {
        DecimalFormat df = new DecimalFormat("#.#", new DecimalFormatSymbols(Locale.US));
        String resultadoFormatado = df.format(resultado);
        this.pilha.clear();
        this.pilha.addAll(Arrays.asList(resultadoFormatado.split("")));
        display.setText(resultadoFormatado);
    }
}