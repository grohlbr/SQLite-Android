package com.rocha.arthur.gerenciarveiculo.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.rocha.arthur.gerenciarveiculo.R;
import com.rocha.arthur.gerenciarveiculo.dao.VeiculoDAO;
import com.rocha.arthur.gerenciarveiculo.model.Veiculo;

import java.util.ArrayList;

public class CreateVehicleActivity extends AppCompatActivity {

    private Spinner spBrand;
    private Spinner spModel;
    private EditText etBoard;
    private EditText etYear;
    private Button btnSave;

    private ArrayList<String> brandList = new ArrayList<>();
    private ArrayList<String> modelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_vehicle);

        findViewById();

        setupList();

        setupAdapter();

        setupClickListener();


        spBrand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "Selecionado, position ="+position, Toast.LENGTH_SHORT).show();
                selectedItemListener(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplicationContext(), "Não selecionado", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void selectedItemListener(int position){

        modelList.clear();

        switch (position){
            case 0:
                modelList.add("Fusca 1200");
                modelList.add("Golf");
                break;

            case 1:
                modelList.add("Camaro");
                modelList.add("Agile");
                modelList.add("Prisma");
                break;

            case 2:
                modelList.add("Uno");
                modelList.add("Bravo");
                modelList.add("500");
                modelList.add("500 Abarth");
                modelList.add("Fiat Mobi");
                modelList.add("Punto");
                break;

            case 3:
                modelList.add("Edge");
                modelList.add("Fusion");
                modelList.add("Ranger");
                break;

            case 4:
                modelList.add("Civic");
                modelList.add("City");
                break;

            default:
                break;
        }

        ArrayAdapter<String> adapterModelList = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item, modelList);
        spModel.setAdapter(adapterModelList);


    }

    private void saveVehicle(){
        Veiculo veiculo = new Veiculo();
        veiculo.setMarca(spBrand.getSelectedItem().toString());
        veiculo.setModelo(spModel.getSelectedItem().toString());
        veiculo.setPlaca(etBoard.getText().toString());
        veiculo.setAno(etYear.getText().toString());

        VeiculoDAO veiculoDAO = new VeiculoDAO(getApplicationContext());
        Long id = veiculoDAO.save(veiculo);

        if(id != null){
            Log.i("Veículo", "Veículo salvo id="+id);
            Toast.makeText(getApplicationContext(), "Veículo salvo", Toast.LENGTH_SHORT).show();
            finish();
        }else{
            Log.i("Veículo","Erro ao salvar Veículo");
            Toast.makeText(getApplicationContext(), "Erro ao salvar Veículo", Toast.LENGTH_SHORT).show();
        }
    }

    private void setupList(){
        brandList.add("Volkswagen");
        brandList.add("GM/Chevrolet");
        brandList.add("Fiat");
        brandList.add("Ford");
        brandList.add("Honda");
    }

    private void setupAdapter(){
        ArrayAdapter<String> adapterBrandList = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item, brandList);
        spBrand.setAdapter(adapterBrandList);
    }

    private void findViewById(){
        spBrand = (Spinner) findViewById(R.id.spBrand);
        spModel = (Spinner) findViewById(R.id.spModel);
        etBoard = (EditText) findViewById(R.id.etBoard);
        etYear = (EditText) findViewById(R.id.etYear);
        btnSave = (Button) findViewById(R.id.btnSave);
    }

    private void setupClickListener(){

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidation()){
                    saveVehicle();
                }
            }
        });
    }

    private boolean isValidation(){
        if(etBoard.getText().toString().isEmpty()){
            etBoard.setError("Campo obrigatório");
            return false;
        }

        if(etYear.getText().toString().isEmpty()){
            etYear.setText("Campo obrigatório");
            return false;
        }
        return true;
    }
}
