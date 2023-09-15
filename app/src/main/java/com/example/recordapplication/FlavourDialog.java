package com.example.recordapplication;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class FlavourDialog extends AppCompatDialogFragment {
    EditText addFlavourInp;
    private FlavourDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_flavour_dialog, null);
        builder.setView(view)
                .setTitle("Add Flavour")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newFlavour = addFlavourInp.getText().toString();
                        listener.applyText(newFlavour);
                    }
                });

        initialiseViews(view);

        return builder.create();
    }

    private void initialiseViews(View view) {
        addFlavourInp = view.findViewById(R.id.flavourInp);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (FlavourDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "Must implement FlavourDialog listener");
        }
    }

    public interface FlavourDialogListener {
        void applyText(String newFlavour);
    }
}
