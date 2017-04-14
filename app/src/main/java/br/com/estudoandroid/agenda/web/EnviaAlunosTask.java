package br.com.estudoandroid.agenda.web;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.List;

import br.com.estudoandroid.agenda.converter.AlunoConverter;
import br.com.estudoandroid.agenda.dao.AlunoDAO;
import br.com.estudoandroid.agenda.modelo.Aluno;

/**
 * Created by Jhonathan on 12/04/2017.
 */

public class EnviaAlunosTask extends AsyncTask<Void,Void,String>{

    private Context context;
    private ProgressDialog dialog;

    public EnviaAlunosTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(context, "Aguarde","Enviando Alunos...", true,true);
    }

    @Override
    protected String doInBackground(Void... params) {
        AlunoDAO dao = new AlunoDAO(context);
        List<Aluno> alunos = dao.buscaAluno();
        dao.close();

        AlunoConverter conversor = new AlunoConverter();
        String json = conversor.converteParaJSON(alunos);

        WebClient client = new WebClient();
        String resposta = client.post(json);

        return resposta;
    }

    @Override
    protected void onPostExecute(String resposta) {
        dialog.dismiss();
        Toast.makeText(context , resposta, Toast.LENGTH_LONG).show();
    }
}
