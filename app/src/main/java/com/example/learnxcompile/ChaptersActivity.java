package com.example.learnxcompile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;
import android.content.DialogInterface;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.example.learnxcompile.Controllers.ChapterController;
import com.example.learnxcompile.Items.Language;

import java.util.List;

public class ChaptersActivity extends AppCompatActivity {

    private ChapterController chapterController;
    private RecyclerView recyclerView;
    private String languageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapters);

        languageName = getIntent().getStringExtra("LANGUAGE_NAME");
        TextView tvLanguageTitle = findViewById(R.id.tvLanguageTitle);
        tvLanguageTitle.setText(languageName + " Chapters");

        chapterController = new ChapterController(this);
        recyclerView = findViewById(R.id.recyclerViewChapters);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadChapters();
    }

    private void loadChapters() {
        Language language = chapterController.getLanguageByName(languageName);
        if (language != null && language.chapters != null) {
            recyclerView.setAdapter(new ChaptersAdapter(language.chapters));
        } else {
            Toast.makeText(this, "No chapters found for " + languageName, Toast.LENGTH_SHORT).show();
        }
    }

    private void showSubscriptionPopUp() {
        new AlertDialog.Builder(this)
                .setTitle("Accès Verrouillé")
                .setMessage("Tu dois t'abonner pour compléter le cours " + languageName + " et débloquer tous les chapitres.")
                .setPositiveButton("S'abonner (5$)", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(ChaptersActivity.this, SubscriptionActivity.class);
                        intent.putExtra("LANGUAGE_NAME", languageName);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Plus tard", null)
                .show();
    }

    private class ChaptersAdapter extends RecyclerView.Adapter<ChaptersAdapter.ViewHolder> {
        private List<Language.Chapter> chapters;

        public ChaptersAdapter(List<Language.Chapter> chapters) {
            this.chapters = chapters;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chapter, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Language.Chapter chapter = chapters.get(position);
            holder.tvNumber.setText(String.valueOf(position + 1));
            holder.tvTitle.setText(chapter.title);
            holder.tvDescription.setText(chapter.description);

            if (chapter.isLocked) {
                holder.ivLock.setImageResource(R.drawable.ic_locked);
                holder.itemView.setAlpha(0.6f);
                holder.itemView.setOnClickListener(v -> showSubscriptionPopUp());
            } else {
                holder.ivLock.setImageResource(R.drawable.ic_unlocked);
                holder.itemView.setAlpha(1.0f);
                holder.itemView.setOnClickListener(v -> {
                    Intent intent = new Intent(ChaptersActivity.this, ChapterExplanationActivity.class);
                    intent.putExtra("CHAPTER_TITLE", chapter.title);
                    intent.putExtra("CHAPTER_ID", chapter.id);
                    intent.putExtra("LANGUAGE_NAME", languageName);
                    startActivity(intent);
                });
            }
        }

        @Override
        public int getItemCount() {
            return chapters.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvNumber, tvTitle, tvDescription;
            ImageView ivLock;

            ViewHolder(View itemView) {
                super(itemView);
                tvNumber = itemView.findViewById(R.id.tvChapterNumber);
                tvTitle = itemView.findViewById(R.id.tvChapterTitle);
                tvDescription = itemView.findViewById(R.id.tvChapterDescription);
                ivLock = itemView.findViewById(R.id.ivLock);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadChapters();
    }
}
