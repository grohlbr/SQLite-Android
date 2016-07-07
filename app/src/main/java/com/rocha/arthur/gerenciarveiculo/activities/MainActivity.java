package com.rocha.arthur.gerenciarveiculo.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.rocha.arthur.gerenciarveiculo.R;
import com.rocha.arthur.gerenciarveiculo.adapters.VehicleListAdapter;
import com.rocha.arthur.gerenciarveiculo.dao.VeiculoDAO;
import com.rocha.arthur.gerenciarveiculo.model.Veiculo;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView lvVehicle;
    private VehicleListAdapter vehicleListAdapter;
    private ArrayList<Veiculo> veiculos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,CreateVehicleActivity.class);
                startActivity(intent);
            }
        });


        lvVehicle = (ListView) findViewById(R.id.lvVehicle);

        getAllVehicles();

        lvVehicle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Veiculo veiculo = veiculos.get(position);

                Intent intent = new Intent(MainActivity.this,UpdateVehicleActivity.class);
                intent.putExtra("vehicle", veiculo);
                startActivity(intent);
            }
        });

        lvVehicle.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Veiculo veiculo = veiculos.get(position);
                deleteVehicle(veiculo);
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAllVehicles();
    }

    private void getAllVehicles(){
        VeiculoDAO veiculoDAO = new VeiculoDAO(getApplicationContext());

        veiculos = veiculoDAO.getAll();

        vehicleListAdapter = new VehicleListAdapter(getApplicationContext(), veiculos);
        lvVehicle.setAdapter(vehicleListAdapter);
    }

    private void deleteVehicle(Veiculo veiculo){
        VeiculoDAO veiculoDAO = new VeiculoDAO(getApplicationContext());

        Integer id = veiculoDAO.delete(veiculo);

        if(id != null){
            getAllVehicles();
            Toast.makeText(getApplicationContext(), "Deletado com Sucesso", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(), "Erro ao deletar", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
