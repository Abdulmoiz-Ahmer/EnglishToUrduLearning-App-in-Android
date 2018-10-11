package com.example.aceahmer.languagedictionary;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Family extends Fragment {
    ArrayList<DataModelClass> numbers;
    MediaPlayer mediaPlayer;
    AudioManager audioManager;
    AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener;

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.family, container, false);
        ListView listView = view.findViewById(R.id.list_OfFamily);
        CustomAdapter customAdapter = new CustomAdapter(this.getActivity(), getFamily(), true);
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

    private ArrayList<DataModelClass> getFamily() {
        numbers = new ArrayList<>();
        numbers.add(new DataModelClass(R.drawable.grandfather_family, "GrandFather", "دادا", R.raw.dada));
        numbers.add(new DataModelClass(R.drawable.grandmother_family, "GrandMother", "دادی", R.raw.dadi));
        numbers.add(new DataModelClass(R.drawable.father_family, "Father", "باپ", R.raw.baba));
        numbers.add(new DataModelClass(R.drawable.mother_family, "Mother", "ماں", R.raw.mom));
        numbers.add(new DataModelClass(R.drawable.uncle_family, "Uncle", "چاچا", R.raw.uncle));
        numbers.add(new DataModelClass(R.drawable.aunt_family, "Aunt", "چاچی", R.raw.aunt));
        numbers.add(new DataModelClass(R.drawable.brother_family, "Brother", "بھائی", R.raw.brother));
        numbers.add(new DataModelClass(R.drawable.sister_family, "Sister", "دیدی", R.raw.sister));
        numbers.add(new DataModelClass(R.drawable.cousins, "Cousins", "کزن", R.raw.cousin));
        numbers.add(new DataModelClass(R.drawable.son_family, "Son", "بیٹا", R.raw.son));
        numbers.add(new DataModelClass(R.drawable.daughter_family, "Daughter", "بیٹی", R.raw.beti));
        return numbers;
    }

    @Override
    public String toString() {
        return "Family";
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
