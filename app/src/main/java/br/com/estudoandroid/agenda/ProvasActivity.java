package br.com.estudoandroid.agenda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import br.com.estudoandroid.agenda.modelo.Prova;

public class ProvasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provas);

        List<String> topicosPort = Arrays.asList("Sujeito","Objeto Direto", "Objeto Indireto");
        Prova provaPortugues = new Prova("Português", "25/05/2016", topicosPort);

        List<String> topicosMat = Arrays.asList("Equacoes de segundo grau","Trigonometria");
        Prova provaMatematica = new Prova("Matemática", "27/05/2016", topicosMat);

        List<Prova> provas = Arrays.asList(provaPortugues, provaMatematica);

        ArrayAdapter<Prova> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,provas);

        ListView lista = (ListView) findViewById(R.id.provas_lista);
        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Prova prova = (Prova) parent.getItemAtPosition(position);
                Toast.makeText(ProvasActivity.this, "Clicou na prova de "+prova,Toast.LENGTH_SHORT).show();
                Intent vaiPraDetalhes = new Intent(ProvasActivity.this, DetalhesProvaActivity.class);
                vaiPraDetalhes.putExtra("prova", prova);
                startActivity(vaiPraDetalhes);
            }
        });

    }
}
