package bastien.todolist.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import bastien.todolist.Data.Task;
import bastien.todolist.R;

/**
 * Cette classe permet de gérer le RecyclerView de TaskActivity qui permet d'afficher en temps réel les tâches de l'utilisateur
 * Si une tâche est supprimer, ajouter, modifier, elle doit être automatiquement être mise à jour visuellement sur l'application, c'est le travail
 * du RecyclerView
 */

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.MyViewHolder> {

    private Context context;
    private List<Task> list;

    public ButtonAdapterListener onClickListener;

    public TaskAdapter(Context context, List<Task> list, ButtonAdapterListener onClickListener) {
        this.context = context;
        this.list = list;
        this.onClickListener = onClickListener;
    }


    /**
     * Ici, on définit le layout que chaque tâche va avoir dans le RecyclerView
     *
     * @param viewGroup
     * @param i
     * @return
     */
    @NonNull
    @Override
    public TaskAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View taskView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_task, viewGroup, false);
        return new MyViewHolder(taskView);
    }

    /**
     * On attribut les valeurs du layout à celles de la tâche
     *
     * @param myViewHolder
     * @param i
     */
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {
        final Task task = list.get(i);

//        myViewHolder.id.setText(String.valueOf(task.getId()));
        myViewHolder.titre.setText(task.getTitre());
        myViewHolder.description.setText(task.getDescription());
        myViewHolder.dateLimite.setText(task.getDateLimite());
        myViewHolder.heureLimite.setText(task.getHeureLimite());

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * Supprime une tâche
     *
     * @param position
     */
    public void removeTask(int position) {

        list.remove(position);
        notifyItemRemoved(position);

    }


    /**
     * Modifier une tâche
     *
     * @param task
     * @param position
     */
    public void editTask(Task task, int position) {
        list.add(position, task);
        notifyItemInserted(position);
    }

    /**
     * On construit le visuel de la tâche en attribuant chaque attribut -> un id
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView titre, description, id, dateLimite,heureLimite;


        public RelativeLayout layout_task;

        public Button share, edit, delete;

        public MyViewHolder(View taskView) {
            super(taskView);

//            id = taskView.findViewById(R.id.id_task);
            titre = taskView.findViewById(R.id.titre_task);
            description = taskView.findViewById(R.id.description_task);
            dateLimite = taskView.findViewById(R.id.dateLimite);
            heureLimite = taskView.findViewById(R.id.heureLimite);


            // layout où l'on va afficher le recyclerView
            layout_task = taskView.findViewById(R.id.layout_task_list);

            share = taskView.findViewById(R.id.buttonPartager);
            edit = taskView.findViewById(R.id.buttonModifier);
            delete = taskView.findViewById(R.id.buttonSupprimer);


            // si l'utilisateur clique sur le bouton partager, on transmet View et la position
            // de l'adapter où le bouton déclenché se trouve à l'interface correspondante qui sera définie dans TaskActivity
            share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.shareOnClick(v, getAdapterPosition());
                }
            });

            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.editOnClick(v, getAdapterPosition());
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.deleteOnClick(v, getAdapterPosition());
                }
            });

        }

    }

    public interface ButtonAdapterListener {

        void shareOnClick(View v, int position);

        void editOnClick(View v, int position);

        void deleteOnClick(View v, int position);
    }


}