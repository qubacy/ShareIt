package com.qubacy.shareit.application.ui.activity._common.page.idea.create;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.qubacy.shareit.application._common.error.ErrorEnum;
import com.qubacy.shareit.application._common.error.model.ErrorReference;
import com.qubacy.shareit.application.ui._common.validator.Validator;
import com.qubacy.shareit.application.ui.activity._common.page._common.base.stateful.StatefulFragment;
import com.qubacy.shareit.application.ui.activity._common.page._common.util.navigation.NavigationFragmentUtil;
import com.qubacy.shareit.application.ui.activity._common.page._common.util.topbar.TopBarFragmentUtil;
import com.qubacy.shareit.application.ui.activity._common.page.idea.create._common.data.IdeaSketch;
import com.qubacy.shareit.application.ui.activity._common.page.idea.create.model._common.IdeaCreateViewModel;
import com.qubacy.shareit.application.ui.activity._common.page.idea.create.model._common.state.IdeaCreateState;
import com.qubacy.shareit.application.ui.activity._common.page.idea.create.model._di.IdeaCreateViewModelFactoryQualifier;
import com.qubacy.shareit.application.ui.activity._common.page.idea.create.validator.content._di.IdeaContentValidatorQualifier;
import com.qubacy.shareit.application.ui.activity._common.page.idea.create.validator.title._di.IdeaTitleValidatorQualifier;
import com.qubacy.shareit.application.ui.activity.main.ShareItActivity;
import com.qubacy.shareit.databinding.FragmentIdeaCreateBinding;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class IdeaCreateFragment extends StatefulFragment<IdeaCreateState, IdeaCreateViewModel> {
    public static final String IS_CREATED_RESULT_KEY = "is_created_result";

    @IdeaTitleValidatorQualifier
    @Inject
    public Validator ideaTitleValidator;
    @IdeaContentValidatorQualifier
    @Inject
    public Validator ideaContentValidator;

    @IdeaCreateViewModelFactoryQualifier
    @Inject
    public ViewModelProvider.Factory viewModelFactory;
    @NotNull
    private IdeaCreateViewModel _model;

    @NotNull
    private FragmentIdeaCreateBinding _binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _model = new ViewModelProvider(this, viewModelFactory).get(IdeaCreateViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(
        @NonNull LayoutInflater inflater,
        @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState
    ) {
        _binding = FragmentIdeaCreateBinding.inflate(inflater, container, false);

        return _binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupTopAppBar();
        setupControls();
    }

    @Override
    protected IdeaCreateViewModel getModel() {
        return _model;
    }

    private void setupTopAppBar() {
        TopBarFragmentUtil.setupTopAppBar(
            (ShareItActivity) requireActivity(),
            this,
            _binding.fragmentIdeaCreateTopbarToolbar
        );
    }

    private void setupControls() {
        _binding.fragmentIdeaCreateSaveButton.setOnClickListener((view) -> onSaveButtonClicked());
        _binding.fragmentIdeaCreateCancelButton.setOnClickListener((view) -> onCancelButtonClicked());
    }

    private void onSaveButtonClicked() {
        final String title = _binding.fragmentIdeaCreateTitleInput.getText().toString();
        final String content = _binding.fragmentIdeaCreateContentInput.getText().toString();

        if (!ideaTitleValidator.validate(title) || !ideaContentValidator.validate(content)) {
            onErrorCaught(new ErrorReference(ErrorEnum.INVALID_IDEA_DATA.id));

            return;
        }

        _model.createIdea(new IdeaSketch(title, content));
    }

    private void onCancelButtonClicked() {
        onFinished();
    }

    @Override
    protected void processState(@NotNull IdeaCreateState state) {
        super.processState(state);

        if (state.isCreated) onIdeaCreated();
        if (state.isLoading) adjustUiWithLoadingState(state.isLoading);
    }

    private void adjustUiWithLoadingState(boolean isLoading) {
        _binding.fragmentIdeaCreateTopbarProgressIndicator
            .setVisibility(isLoading ? View.VISIBLE : View.INVISIBLE);
    }

    private void onIdeaCreated() {
        NavigationFragmentUtil.setDestinationResult(this, IS_CREATED_RESULT_KEY, true);

        onFinished();
    }

    private void onFinished() {
        NavHostFragment.findNavController(this).navigateUp();
    }
}
