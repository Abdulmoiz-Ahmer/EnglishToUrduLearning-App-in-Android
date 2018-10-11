package com.example.aceahmer.languagedictionary;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class Phrases extends Fragment {
    ArrayList<DataModelClass> numbers;
    MediaPlayer mediaPlayer;
    AudioManager audioManager;
    AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.phases, container, false);
        ListView listView = view.findViewById(R.id.Items);
        CustomAdapter customAdapter = new CustomAdapter(this.getActivity(), getPhases(), false);
        listView.setAdapter(customAdapter);
        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        mediaPlayer = MediaPlayer.create(getActivity(), R.raw.aunt);
        onAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
            @Override
            public void onAudioFocusChange(int i) {
                if (i == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || i == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                    mediaPlayer.pause();
                    mediaPlayer.seekTo(0);
                } else if (i == AudioManager.AUDIOFOCUS_LOSS) {
                    releaseMediaPlayer();
                } else if (i == AudioManager.AUDIOFOCUS_GAIN) {
                    mediaPlayer.start();
                }

            }
        };
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if ((audioManager.requestAudioFocus(onAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT) == AudioManager.AUDIOFOCUS_REQUEST_GRANTED)) {
                    mediaPlayer = MediaPlayer.create(getActivity(), numbers.get(i).getAudioFile());
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            releaseMediaPlayer();
                        }
                    });

                }


            }
        });


        return view;
    }

    private ArrayList<DataModelClass> getPhases() {
        numbers = new ArrayList<>();
        numbers.add(new DataModelClass(R.drawable.ic_launcher_background, "What is your name?","آپ کا نام کیا ہے؟",R.raw.phrase1));
        numbers.add(new DataModelClass(R.drawable.ic_launcher_background, "What is your age?", "آپ کی عمر کیا ہے؟",R.raw.phrase2));
        numbers.add(new DataModelClass(R.drawable.ic_launcher_background, "How are you?", "تم کیسی ہو؟",R.raw.phrase3));
        numbers.add(new DataModelClass(R.drawable.ic_launcher_background, "How was your day?", "آپ کا دن کیسا گزرا؟",R.raw.phrase4));
        numbers.add(new DataModelClass(R.drawable.ic_launcher_background, "How is your job going?", "تمہارا کام کیسے چل رہا ہے؟",R.raw.phrase5));
        return numbers;
    }
    @Override
    public String toString() {
        return "Phase";
    }
    public void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
            audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
            audioManager.abandonAudioFocus(onAudioFocusChangeListener);

        }
    }

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

}
