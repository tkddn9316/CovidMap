package app.map.covid.base;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import org.jetbrains.annotations.NotNull;

public class FViewModelFactory extends ViewModelProvider.AndroidViewModelFactory {
    private Activity activity;
    private Object data;

    private FViewModelFactory(@NonNull Activity activity) {
        super(activity.getApplication());
        this.activity = activity;
    }

    private FViewModelFactory(@NonNull Activity activity, @NonNull Object data) {
        this(activity);
        this.data = data;
    }

    @NonNull
    public static FViewModelFactory getInstance(@NonNull Activity activity) {
        return new FViewModelFactory(activity);
    }

    @NonNull
    public static FViewModelFactory getInstance(@NonNull Activity activity, @NotNull Object data) {
        return new FViewModelFactory(activity, data);
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        try {

        }catch (Exception e) {
            e.printStackTrace();
        }
        return super.create(modelClass);
    }
}
