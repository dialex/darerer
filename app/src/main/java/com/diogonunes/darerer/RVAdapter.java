package com.diogonunes.darerer;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.DiceBoxViewHolder> {

    List<DiceBox> _diceBoxes;

    public RVAdapter(List<DiceBox> diceBoxes) {
        this._diceBoxes = diceBoxes;
    }

    @Override
    public int getItemCount() {
        return _diceBoxes.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public DiceBoxViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.content_main, viewGroup, false);
        return new DiceBoxViewHolder(v);
    }

    @Override
    public void onBindViewHolder(DiceBoxViewHolder diceBoxViewHolder, int i) {
        diceBoxViewHolder.cardTitle.setText(_diceBoxes.get(i).getTitle());
        diceBoxViewHolder.cardDesc.setText(_diceBoxes.get(i).getDescription());
    }

    public static class DiceBoxViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView cardTitle;
        TextView cardDesc;

        DiceBoxViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.card);
            cardTitle = (TextView) itemView.findViewById(R.id.card_title);
            cardDesc = (TextView) itemView.findViewById(R.id.card_desc);
        }
    }

}
