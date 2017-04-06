package br.com.estudoandroid.agenda.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.estudoandroid.agenda.ListaAlunosActivity;
import br.com.estudoandroid.agenda.modelo.Aluno;

/**
 * Created by Jhonathan on 06/04/2017.
 */

public class AlunosAdapter extends BaseAdapter {

    private final List<Aluno> alunos;
    private final Context context;

    public AlunosAdapter(Context context, List<Aluno> alunos) {
        this.context = context;
        this.alunos = alunos;
    }

    @Override
    public int getCount() {
        return alunos.size();
    }

    @Override
    public Object getItem(int position) {
        return alunos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return alunos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView view = new TextView(context);
        Aluno aluno = alunos.get(position);
        view.setText(aluno.toString());
        return view;
    }
}
