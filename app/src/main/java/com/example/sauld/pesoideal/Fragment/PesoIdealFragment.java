package com.example.sauld.pesoideal.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.sauld.pesoideal.R;


public class PesoIdealFragment extends Fragment {

    private String format;
    private Button btnCalcular;
    private EditText editPeso;
    private EditText editAltura;
    private TextView txtGenero;
    private ImageView imageViewMen;
    private ImageView imageViewWomen;
    private RadioGroup radioGroupAltura;
    private RadioGroup radioGroupPeso;
    private TextView txtEstado;
    private TextView txtPesoIdeal;
    private TextView txtShowIMC;
    private TextView txtPesoIdealLib;
    private float peso;
    private float altura;
    private TextView txt;

    public PesoIdealFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista;
        vista = inflater.inflate(R.layout.fragment_peso_ideal, container, false);
        editPeso = (EditText) vista.findViewById(R.id.txtPeso);
        editAltura = (EditText) vista.findViewById(R.id.txtAltura);
        radioGroupAltura = (RadioGroup) vista.findViewById(R.id.groupAltura);
        radioGroupPeso = (RadioGroup) vista.findViewById(R.id.groupPeso);
        btnCalcular = (Button) vista.findViewById(R.id.btnCalcular);
        txtGenero = (TextView) vista.findViewById(R.id.txtgenero);
        imageViewMen = (ImageView) vista.findViewById(R.id.ic_men);
        imageViewWomen = (ImageView) vista.findViewById(R.id.ic_women);
        txtEstado = (TextView) vista.findViewById(R.id.estado);
        txtPesoIdeal = (TextView) vista.findViewById(R.id.pesoideal);
        txtShowIMC = (TextView) vista.findViewById(R.id.imcshow);
        txtPesoIdealLib = (TextView) vista.findViewById(R.id.pesoidealLib);
        txt = (TextView) vista.findViewById(R.id.txtTitle);
        format = "%.1f";
        //    format = new DecimalFormat();
        ejecutar();
        return vista;
    }

    private void ejecutar() {
        obtenerGenero();
        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                obtenerDatosEditTex();
                ocultarTeclado(v);
            }

        });

    }

    private void ocultarTeclado(View v) {
        if (v != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    //Con esta formula el resultado, lo muestra en kilogramo
    private void formulaWanderVael() {
        float pesoIdeal;
        String genero;
        genero = txtGenero.getText().toString();
        if (genero.equals("Hombre")) {
            pesoIdeal = (float) (((altura - 150) * 0.75) + 50);
        } else {
            pesoIdeal = (float) (((altura - 150) * 0.6) + 50);
        }
        mostrarInfo(pesoIdeal);
    }

    //Mostrar informacion

    private void mostrarInfo(float pesoIdeal) {
        String kg = String.format(format, pesoIdeal) + getResources().getString(R.string.kgt);
        String lib = String.format(format, convertirALb(pesoIdeal)) + getResources().getString(R.string.lbt);
        txtPesoIdeal.setText(kg);
        txtPesoIdealLib.setText(lib);
        txtEstado.setVisibility(View.VISIBLE);
        txt.setVisibility(View.VISIBLE);

    }

    // Obteniendo el genero
    private void obtenerGenero() {
        imageViewWomen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtGenero.setText(imageViewWomen.getTag().toString());
                animate(imageViewWomen);

            }
        });
        imageViewMen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtGenero.setText(imageViewMen.getTag().toString());
                animate(imageViewMen);
            }
        });
    }

    //Animacion para elegir el genero en el ImageView
    private void animate(ImageView img){
        img.setRotationX(-10000f);
        img.animate().rotationXBy(10000f).setDuration(500);
    }

    //Este metodo es para verificar que tipos de datos se ingresara si es en Pies o CM
//    public float conversorAltura(float altura) {
//        int checked = radioGroupAltura.getCheckedRadioButtonId();
//        if (checked == R.id.ps) {
//            Toast.makeText(getContext(), "Pies", Toast.LENGTH_SHORT).show();
//        }
//        return altura;
//    }
//
//    public float conversorPeso(float peso) {
//        int checked = radioGroupPeso.getCheckedRadioButtonId();
//        if (checked == R.id.kg) {
//            convertirALb(peso);
//            Toast.makeText(getContext(), "Kilogramo", Toast.LENGTH_SHORT).show();
//        }
//        return peso;
//    }


    private void showimc(float imc) {
        if (imc < 18.5) {
            txtEstado.setText(getResources().getString(R.string.pesoBajo));
            txtEstado.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.amarillo));
        }
        if (imc > 18.5 && imc < 24.9) {
            txtEstado.setText(getResources().getString(R.string.normal));
            txtEstado.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.verde));
        }
        if (imc > 30) {
            txtEstado.setText(getResources().getString(R.string.sobrepeso));
            txtEstado.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.rojoF));
        }
        if (imc > 24.9 && imc < 29.9) {
            txtEstado.setText(getResources().getString(R.string.obeso));
            txtEstado.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.rojo));
        }
    }

    //Este metodo convierte de LIB a KG
    private float convertirAkg(float peso) {
        return peso / 2.2046f;
    }

    //Este metodo convierte de kg a lb
    private float convertirALb(float peso) {
        return peso * 2.2046f;
    }

    //Convertimos de CM a Metros
    private float convertirCMaM(float altura) {
        return altura / 100;
    }

    //Convertimos de pies a CM
    private float convertirPiesAcm(float altura) {
        return altura * 30.4f;
    }

    //Formula del IMC
    private void formulaIMC() {
        float imc;
        peso = convertirAkg(peso);
        altura = convertirCMaM(altura);
        imc = (float) (peso / Math.pow(altura, 2));
        String imcString = getResources().getString(R.string.imc) + String.format(format, imc);
        txtShowIMC.setText(imcString);
        showimc(imc);
    }

    //Aqui obtenemos los datos de los EditText, evaluando el tipo de datos que se ingresa si es KG, CM, Pies, Lib.
    private void obtenerDatosEditTex() {
        if (!TextUtils.isEmpty(editAltura.getText()) && !TextUtils.isEmpty(editPeso.getText())) {
            peso = Integer.parseInt(editPeso.getText().toString());
            altura = Float.parseFloat(editAltura.getText().toString());
            int checkedPeso = radioGroupPeso.getCheckedRadioButtonId();
            int checkedAltura = radioGroupAltura.getCheckedRadioButtonId();
            if (checkedPeso == R.id.kg) {
                peso = convertirALb(peso);
            }
            if (checkedAltura == R.id.ps) {
                altura = convertirPiesAcm(altura);
            }
            formulaWanderVael();
            formulaIMC();
        }
    }


}
