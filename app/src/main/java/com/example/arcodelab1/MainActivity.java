package com.example.arcodelab1;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.ViewRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.BaseArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ArFragment arFragment;
    private ModelRenderable bearRenderable,
            catRendarable,
            dogRendarable, elephantRendarable, koalaRendarable;
    ImageView bear, cat, dog, elephant, ferret, koala, lion;
    View array_view[];
    ViewRenderable animalName;
    int selected = 1; // default bear is selected

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.sceneform_ux_fragment);

        // imageViews
        bear = findViewById(R.id.bear);
        cat = findViewById(R.id.cat);
        dog = findViewById(R.id.dog);
        elephant = findViewById(R.id.elephant);
        koala = findViewById(R.id.koala_bear);

        setArrayView();
        setClickListener();
        arFragment.setOnTapArPlaneListener(new BaseArFragment.OnTapArPlaneListener() {
            @Override
            public void onTapPlane(HitResult hitResult, Plane plane, MotionEvent motionEvent) {
                Anchor anchor = hitResult.createAnchor();
                AnchorNode anchorNode = new AnchorNode(anchor);
                anchorNode.setParent(arFragment.getArSceneView().getScene());
                createModel(anchorNode, selected);
            }
        });

        initResources();
    }

    private void createModel(AnchorNode anchorNode, int selected) {
        if (selected == 1) {
            TransformableNode transformableNodeBear = new TransformableNode(arFragment.getTransformationSystem());
            transformableNodeBear.setParent(anchorNode);
            transformableNodeBear.setRenderable(bearRenderable);
            transformableNodeBear.select();

            addName(anchorNode, transformableNodeBear, "Bear");
        }
        if (selected == 2) {
            TransformableNode transformableNode = new TransformableNode(arFragment.getTransformationSystem());
            transformableNode.setParent(anchorNode);
            transformableNode.setRenderable(catRendarable);
            transformableNode.select();

            addName(anchorNode, transformableNode, "Cat");
        }
        if (selected == 3) {
            TransformableNode transformableNode = new TransformableNode(arFragment.getTransformationSystem());
            transformableNode.setParent(anchorNode);
            transformableNode.setRenderable(dogRendarable);
            transformableNode.select();

            addName(anchorNode, transformableNode, "Dog");
        }

        if (selected == 4) {
            TransformableNode transformableNode = new TransformableNode(arFragment.getTransformationSystem());
            transformableNode.setParent(anchorNode);
            transformableNode.setRenderable(elephantRendarable);
            transformableNode.select();

            addName(anchorNode, transformableNode, "Elephant");
        }

        if (selected == 5) {
            TransformableNode transformableNode = new TransformableNode(arFragment.getTransformationSystem());
            transformableNode.setParent(anchorNode);
            transformableNode.setRenderable(koalaRendarable);
            transformableNode.select();

            addName(anchorNode, transformableNode, "Koala Bear");
        }
    }

    private void addName(AnchorNode anchorNode, TransformableNode transformableNode, String name) {
        TransformableNode transformable = new TransformableNode(arFragment.getTransformationSystem());
        transformable.setLocalPosition(new Vector3(0f, transformableNode.getLocalPosition().y + 0.5f, 0));
        transformable.setParent(anchorNode);
        transformable.setRenderable(animalName);
        transformable.select();

        // set text to model
        TextView tv = (TextView) animalName.getView();
        tv.setText(name);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anchorNode.setParent(null);
            }
        });
    }


    private void initResources() {
        ViewRenderable.builder()
                .setView(this, R.layout.animal_name)
                .build()
                .thenAccept(modelRendarable -> animalName = modelRendarable)
                .exceptionally(
                        throwable -> {
                            Toast.makeText(this, "Unable to load model", Toast.LENGTH_SHORT).show();
                            return null;
                        });

        ModelRenderable.builder()
                .setSource(this, R.raw.bear)
                .build()
                .thenAccept(modelRenderable -> bearRenderable = modelRenderable)
                .exceptionally(
                        throwable -> {
                            Toast.makeText(this, "Unable to load model", Toast.LENGTH_SHORT).show();
                            return null;
                        });

        ModelRenderable.builder()
                .setSource(this, R.raw.cat)
                .build()
                .thenAccept(modelRenderable -> catRendarable = modelRenderable)
                .exceptionally(throwable -> {
                    Toast.makeText(this, "cannot load cat model", Toast.LENGTH_SHORT).show();
                    return null;
                });

        ModelRenderable.builder()
                .setSource(this, R.raw.dog)
                .build()
                .thenAccept(modelRenderable -> dogRendarable = modelRenderable)
                .exceptionally(throwable -> {
                    Toast.makeText(this, "cannot load dog model", Toast.LENGTH_SHORT).show();
                    return null;
                });

        ModelRenderable.builder()
                .setSource(this, R.raw.elephant)
                .build()
                .thenAccept(modelRenderable -> elephantRendarable = modelRenderable)
                .exceptionally(throwable -> {
                    Toast.makeText(this, "cannot load elephant model", Toast.LENGTH_SHORT).show();
                    return null;
                });

        ModelRenderable.builder()
                .setSource(this, R.raw.koala_bear)
                .build()
                .thenAccept(modelRenderable -> koalaRendarable = modelRenderable)
                .exceptionally(throwable -> {
                    Toast.makeText(this, "cannot load koala model", Toast.LENGTH_SHORT).show();
                    return null;
                });
    }

    private void setClickListener() {
        for (View view : array_view) {
            view.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.bear) {
            selected = 1;
            setBackground(view.getId());
        } else if (view.getId() == R.id.cat) {
            selected = 2;
            setBackground(view.getId());
        } else if (view.getId() == R.id.dog) {
            selected = 3;
            setBackground(view.getId());
        } else if (view.getId() == R.id.elephant) {
            selected = 4;
            setBackground(view.getId());
        } else if (view.getId() == R.id.koala_bear) {
            selected = 5;
            setBackground(view.getId());
        }
    }

    private void setArrayView() {
        array_view = new View[]
                {bear, cat, dog, elephant, koala};
    }

    private void setBackground(int id) {
        for (View view : array_view) {
            if (view.getId() == id) {
                view.setBackgroundColor(Color.parseColor("#80333639"));
            } else {
                view.setBackgroundColor(Color.TRANSPARENT);
            }

        }
    }
}
