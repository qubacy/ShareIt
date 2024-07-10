package com.qubacy.shareit.application.ui.activity.page.idea.list.component.list.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.qubacy.shareit.application.ui.activity.page.idea._common.presentation.IdeaPresentation;
import com.qubacy.shareit.databinding.ComponentIdeaListItemBinding;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class IdeaListRecyclerViewAdapter extends ListAdapter<
    IdeaPresentation,
    IdeaListRecyclerViewAdapter.ViewHolder
> {
    public class ViewHolder extends RecyclerView.ViewHolder {
        @NotNull
        private final ComponentIdeaListItemBinding _binding;

        public ViewHolder(@NonNull ComponentIdeaListItemBinding binding) {
            super(binding.getRoot());

            _binding = binding;
        }

        public void setData(@NotNull IdeaPresentation ideaPresentation) {
            _binding.componentIdeaListItemTitle.setText(ideaPresentation.title);
            _binding.componentIdeaListItemContent.setText(ideaPresentation.content);
        }
    }

    public static final DiffUtil.ItemCallback<IdeaPresentation> DIFF_CALLBACK =
        new DiffUtil.ItemCallback<IdeaPresentation>() {
            @Override
            public boolean areItemsTheSame(
                @NonNull IdeaPresentation oldIdea,
                @NonNull IdeaPresentation newIdea
            ) {
                return Objects.equals(oldIdea.uid, newIdea.uid);
            }

            @Override
            public boolean areContentsTheSame(
                @NonNull IdeaPresentation oldIdea,
                @NonNull IdeaPresentation newIdea
            ) {
                return oldIdea.equals(newIdea);
            }
        };

    public IdeaListRecyclerViewAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final ComponentIdeaListItemBinding itemBinding = ComponentIdeaListItemBinding.inflate(
            inflater, parent, false
        );

        return new ViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final IdeaPresentation item = getItem(position);

        holder.setData(item);
    }
}
