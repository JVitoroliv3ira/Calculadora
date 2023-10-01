package com.example.calculadora.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.calculadora.R;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CalculadoraNotas extends Fragment {
    private final float APROVADO = 7;
    private final float APROVADO_POR_NOTA = 5;

    public CalculadoraNotas() {

    }

    public static CalculadoraNotas newInstance() {
        return new CalculadoraNotas();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calculadora_notas, container, false);
        final Button botaoCalcular = view.findViewById(R.id.btn_calcular_nota);
        final EditText editTextPrimeiraNota = view.findViewById(R.id.et_nota_01);
        final EditText editTextSegundaNota = view.findViewById(R.id.et_nota_02);
        final EditText editTextTerceiraNota = view.findViewById(R.id.et_nota_03);
        final TextView textViewResultado = view.findViewById(R.id.textViewSituacao);

        botaoCalcular.setOnClickListener(v -> {
            Float primeiraNota = this.buscarValorEditText(editTextPrimeiraNota);
            Float segundaNota = this.buscarValorEditText(editTextSegundaNota);
            Float terceiraNota = this.buscarValorEditText(editTextTerceiraNota);
            this.calcular(primeiraNota, segundaNota, terceiraNota, textViewResultado);
        });

        return view;
    }

    private Float buscarValorEditText(EditText editText) {
        if (Boolean.TRUE.equals(Objects.isNull(editText) || Objects.isNull(editText.getText()) || editText.getText().toString().isEmpty())) {
            return null;
        }

        try {
            return Float.parseFloat(editText.getText().toString());
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    private void calcular(Float primeiraNota, Float segundaNota, Float terceiraNota, TextView resultado) {
        if (Boolean.TRUE.equals(Objects.nonNull(primeiraNota) && Objects.nonNull(segundaNota) && Objects.nonNull(terceiraNota))) {
            Float media = this.calcularMediaDasNotas(primeiraNota, segundaNota, terceiraNota);
            this.exibirSituacao(media, resultado);
        } else if (Boolean.TRUE.equals(Objects.nonNull(primeiraNota) && Objects.isNull(segundaNota) && Objects.isNull(terceiraNota))) {
            Map<String, Float> notas = this.calcularSegundaETerceiraNota(primeiraNota);
            this.exibirNotas(notas.get("aprovado"), notas.get("aprovado_por_nota"), resultado);
        } else if (Boolean.TRUE.equals(Objects.nonNull(primeiraNota) && Objects.nonNull(segundaNota) && Objects.isNull(terceiraNota))) {
            Map<String, Float> notas = this.calcularTerceiraNota(primeiraNota, segundaNota);
            this.exibirNotas(notas.get("aprovado"), notas.get("aprovado_por_nota"), resultado);
        } else {
            Toast.makeText(getContext(), "Operação não disponível", Toast.LENGTH_LONG).show();
        }
    }

    private Float calcularMediaDasNotas(Float primeiraNota, Float segundaNota, Float terceiraNota) {
        return (primeiraNota + segundaNota + terceiraNota) / 3;
    }

    private void exibirSituacao(Float media, TextView resultado) {
        if (Boolean.TRUE.equals(media >= 7.0)) {
            resultado.setText(String.format("Situação: Você foi aprovado com média %.2f", media));
            resultado.setTextColor(Color.GREEN);
        } else if (Boolean.TRUE.equals(media >= 5.0 && media < 7)) {
            resultado.setText(String.format("Situação: Você foi aprovado por nota com média %.2f", media));
            resultado.setTextColor(Color.DKGRAY);
        } else {
            resultado.setText(String.format("Situação: Você foi reprovado com média %.2f", media));
            resultado.setTextColor(Color.RED);
        }
    }

    private Map<String, Float> calcularSegundaETerceiraNota(Float primeiraNota) {
        Map<String, Float> notas = new HashMap<>();
        notas.put("aprovado", (3 * this.APROVADO - primeiraNota) / 2);
        notas.put("aprovado_por_nota", (3 * this.APROVADO_POR_NOTA - primeiraNota) / 2);

        return notas;
    }

    private Map<String, Float> calcularTerceiraNota(Float primeiraNota, Float segundaNota) {
        Map<String, Float> notas = new HashMap<>();
        notas.put("aprovado", 3 * this.APROVADO - primeiraNota - segundaNota);
        notas.put("aprovado_por_nota", 3 * this.APROVADO_POR_NOTA - primeiraNota - segundaNota);

        return notas;
    }

    private void exibirNotas(Float notaAprovado, Float notaAprovadoPorNota, TextView resultado) {

        if (Boolean.TRUE.equals(notaAprovado > 10 && notaAprovadoPorNota > 10)) {
            resultado.setText("Não existe possibilidade para ser aprovado.");
            resultado.setTextColor(Color.RED);
        } else {
            resultado.setText("");
            if (Boolean.TRUE.equals(notaAprovado <= 10)) {
                resultado.append(String.format("Para ser aprovado, você precisa tirar %.2f na(s) próxima(s) unidade(s)", notaAprovado));
            }
            if (Boolean.TRUE.equals(notaAprovadoPorNota <= 10)) {
                resultado.append(String.format("\nPara ser aprovado por nota, você precisa tirar %.2f na(s) próxima(s) unidade(s)", notaAprovadoPorNota));
            }
            resultado.setTextColor(Color.DKGRAY);
        }
    }
}