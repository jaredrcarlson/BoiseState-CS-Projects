#include "graph.h"

Graph::Graph() {
}

Graph::Graph(int size) {
    grid_size = size;
}

void Graph::set_grid_size(int size) {
	grid_size = size;
}

void Graph::graph_it(Line l) {
    for(int y_value = grid_size; y_value >= -grid_size; y_value--) {
        for(int x_value = -grid_size; x_value <= grid_size; x_value++) {
            if(l.contains_coord(x_value, y_value)) {
                cout << "*";
            }
            else if(y_value == 0) {
                cout << "-";
            }
            else if(x_value == 0) {
                cout << "|";
            }
            else {
                cout << " ";
            }
        } 
        cout << "\n";  
    }
}
