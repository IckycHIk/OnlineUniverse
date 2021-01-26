package com.devforyou.onlineunivers.Fragment.User;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.devforyou.onlineunivers.R;

import org.jetbrains.annotations.Nullable;

import java.util.Random;

import de.blox.graphview.BaseGraphAdapter;
import de.blox.graphview.Graph;
import de.blox.graphview.GraphView;
import de.blox.graphview.Node;
import de.blox.graphview.ViewHolder;
import de.blox.graphview.energy.FruchtermanReingoldAlgorithm;
import de.blox.graphview.layered.SugiyamaAlgorithm;
import de.blox.graphview.tree.BuchheimWalkerAlgorithm;
import de.blox.graphview.tree.BuchheimWalkerConfiguration;

public class SkillPoints extends Fragment {

 private Context context;
private BaseGraphAdapter<ViewHolder> adapter;
    private int nodeCount = 1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.skills_points,container,false);
        context = getActivity();


        GraphView graphView = view.findViewById(R.id.graph);

        graphView.setUseMaxSize(false);
          graphView.setZoomEnabled(false);
        // example tree
        final Graph graph = new Graph();
        final Node node1 = new Node(getNodeText());
        final Node node2 = new Node(getNodeText());
        final Node node3 = new Node(getNodeText());
        final Node node4 = new Node(getNodeText());
        final Node node5 = new Node(getNodeText());
        final Node node6 = new Node(getNodeText());
        final Node node8 = new Node(getNodeText());
        final Node node7 = new Node(getNodeText());
        final Node node9 = new Node(getNodeText());
        final Node node10 = new Node(getNodeText());
        final Node node11 = new Node(getNodeText());
        final Node node12 = new Node(getNodeText());
        
        graph.addEdge(node1, node2);
        graph.addEdge(node1, node3);
        graph.addEdge(node1, node4);
        graph.addEdge(node2, node5);
        graph.addEdge(node2, node6);
        graph.addEdge(node6, node7);
        graph.addEdge(node6, node8);
        graph.addEdge(node4, node9);
        graph.addEdge(node4, node10);
        graph.addEdge(node4, node11);
        graph.addEdge(node11, node12);


        // you can set the graph via the constructor or use the adapter.setGraph(Graph) method
          adapter = new BaseGraphAdapter<ViewHolder>(graph) {

            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.node, parent, false);
                return new SkillsViewHolder(view);
            }

            @Override
            public void onBindViewHolder(ViewHolder viewHolder, Object data, int position) {
                //((SkillsViewHolder)viewHolder).textView.setText(data.toString());
            }
        };

        // set the algorithm here
        final BuchheimWalkerConfiguration configuration = new BuchheimWalkerConfiguration.Builder()
                .setSiblingSeparation(10)
                .setLevelSeparation(200)
                .setSubtreeSeparation(100)
                .setOrientation(BuchheimWalkerConfiguration.ORIENTATION_TOP_BOTTOM)
                .build();

        adapter.setAlgorithm(new BuchheimWalkerAlgorithm(configuration));

         graphView.setAdapter(adapter);

        return  view;
    }

    private String getNodeText() {
        return "Node " + nodeCount++;
    }






}
