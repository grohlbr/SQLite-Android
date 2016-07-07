package com.rocha.arthur.gerenciarveiculo.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.rocha.arthur.gerenciarveiculo.R;
import com.rocha.arthur.gerenciarveiculo.dao.VeiculoDAO;
import com.rocha.arthur.gerenciarveiculo.model.Veiculo;

import java.util.ArrayList;

public class UpdateVehicleActivity extends AppCompatActivity {

    private Spinner spBrandUpdate;
    private Spinner spModelUpdate;
    private EditText etBoardUpdate;
    private EditText etYearUpdate;
    private Button btnUpdate;

    private ArrayList<String> brandList = new ArrayList<>();
    private ArrayList<String> modelList = new ArrayList<>();

    private Veiculo veiculo;
    private ArrayAdapter<String> adapterModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_vehicle);

        findViewById();

        Intent intent = getIntent();
        veiculo = (Veiculo) intent.getSerializableExtra("vehicle");

        setupList();

        etBoardUpdate.setText(veiculo.getPlaca());
        etYearUpdate.setText(veiculo.getAno());

        spBrandUpdate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedItemListener(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValidation()){
                    updateVehicle();
                }
            }
        });

    }

    private void findViewById(){
        spBrandUpdate = (Spinner) findViewById(R.id.spBrandUpdate);
        spModelUpdate = (Spinner) findViewById(R.id.spModelUpdate);
        etBoardUpdate = (EditText) findViewById(R.id.etBoardUpdate);
        etYearUpdate = (EditText) findViewById(R.id.etYearUpdate);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
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

        adapterModelList = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item, modelList);
        spModelUpdate.setAdapter(adapterModelList);

        Integer positionModel = adapterModelList.getPosition(veiculo.getModelo());
        if(positionModel != null){
            spModelUpdate.setSelection(positionModel);
        }


    }

    private void setupList(){
        brandList.add("Volkswagen");
        brandList.add("GM/Chevrolet");
        brandList.add("Fiat");
        brandList.add("Ford");
        brandList.add("Honda");

        ArrayAdapter<String> adapterBrandList = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item, brandList);
        spBrandUpdate.setAdapter(adapterBrandList);

        int position = adapterBrandList.getPosition(veiculo.getMarca());
        spBrandUpdate.setSelection(position);
    }

    private boolean isValidation(){
        if(etBoardUpdate.getText().toString().isEmpty()){
            etBoardUpdate.setError("Campo obrigatório");
            return false;
        }

        if(etYearUpdate.getText().toString().isEmpty()){
            etYearUpdate.setText("Campo obrigatório");
            return false;
        }
        return true;
    }

    private void updateVehicle(){
        veiculo.setMarca(spBrandUpdate.getSelectedItem().toString());
        veiculo.setModelo(spModelUpdate.getSelectedItem().toString());
        veiculo.setPlaca(etBoardUpdate.getText().toString());
        veiculo.setAno(etYearUpdate.getText().toString());

        VeiculoDAO veiculoDAO = new VeiculoDAO(getApplicationContext());
        Integer id = veiculoDAO.update(veiculo);

        if(id != null){
            Log.i("Veículo", "Veículo salvo id="+id);
            Toast.makeText(getApplicationContext(), "Veículo atualizado", Toast.LENGTH_SHORT).show();
            finish();
        }else{
            Log.i("Veículo","Erro ao salvar Veículo");
            Toast.makeText(getApplicationContext(), "Erro ao atualizar Veículo", Toast.LENGTH_SHORT).show();
        }
    }
}
