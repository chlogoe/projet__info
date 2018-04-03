package Model;

import View.Window;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class Game implements DeletableObserver {
    private ArrayList<GameObject> terrains = new ArrayList<GameObject>();//une ArrayList pour le terrain
    private ArrayList<GameObject> entities = new ArrayList<GameObject>();//Et une pour les entités, permet de dessiner les entités par dessus le sol.

    private Window window;
    private int size;
    private int lineNumber;

    public Game(Window window) throws IOException {
        this.window = window;
        size = window.getSize();

        // Creating one Player at the center of the map
        entities.add(new Player(size/2, size/2, 3, 5));
        MonstreTest monstre = new MonstreTest(size/2 -5, size/2, window);
        monstre.attachDeletable(this);
        entities.add(monstre);

        // Map building
        this.textMapToList();
        

        window.setGameObjects(this.getGameObjects());//Une fois le terrain et toutes les entités dans les liste,
        window.setOther(entities);//on ajoute les listes à la map et on rafraichit la map
        notifyView();
    }


    public void movePlayer(int x, int y, int playerNumber) { //Vérifie si le joueur peut bouger, et le bouge
        Player player = ((Player) entities.get(playerNumber));
        int nextX = player.getPosX() + x;
        int nextY = player.getPosY() + y;

        boolean obstacle = false;
        for (GameObject object : terrains) {
            if (object.isAtPosition(nextX, nextY)) {//regarde si il y a un obstacle devant
                obstacle = object.isObstacle();
            }
            if (obstacle == true) { //si obstacle, on ne bouge pas
                break;
            }
        }
        if (obstacle == false) {
            player.move(x, y);
        }
        notifyView();
    }
    
    public void attack(String side) {
    	int x = entities.get(0).getPosX();
    	int y = entities.get(0).getPosY();
    	switch(side) {
    	case "LEFT" :
    		x--;
    		break;
    	case "RIGHT":
    		x++;
    		break;
    	case "UP":
    		y--;
    		break;
    	case "DOWN":
    		y++;
    		break;
    	}
    	Activable aimedObject = null;
		for(GameObject object : entities){
			if(object.isAtPosition(x,y)){
			    if(object instanceof Activable){
			        aimedObject = (Activable) object;
			    }
			}
		}
		if(aimedObject != null){
		    aimedObject.activate();
            notifyView();
		}
    }

    private void notifyView() {
        window.update();
    }

    public ArrayList<GameObject> getGameObjects() {
        return this.terrains;
    }

    @SuppressWarnings("unlikely-arg-type")
	@Override
    synchronized public void delete(Deletable ps, ArrayList<GameObject> loot) {
        entities.remove(ps);
        if (loot != null) {
            terrains.addAll(loot);
        }
        notifyView();
    }


    public void playerPos(int playerNumber) {
        Player player = ((Player) entities.get(playerNumber));
        System.out.println(player.getPosX() + ":" + player.getPosY());
        
    }
    
    private void addObject(int x, int y, char ID) {
    	switch(ID){
		case 'A':
			terrains.add(new BlockUnbreakable(x, y));
			break;
		case 'B':
			BlockBreakable block = new BlockBreakable(x, y);
            block.attachDeletable(this);
            terrains.add(block);
			break;
		case 'C':
			terrains.add(new BlockWater(x, y));
			break;
		case 'H':
			terrains.add(new Hole(x,y));
			break;
		default:
			//Créer un block par défaut
		}
    }
    
    private void textMapToList() throws IOException {
    	FileReader file = null;
        BufferedReader in = null;
        
        try {
        	file = new FileReader("maps/map2.txt"); //On ouvre le fichier de la map souhaité
        	in = new BufferedReader(file);//On met le fichier en mémoire
        	String line;
        	lineNumber = 0;
        	while((line = in.readLine()) != null) {//On lit chaque ligne jusqu'à une ligne vide
        		for(int i = 0; i < size ; i++) {
        			char ID = line.charAt(i);//Le type de terrain est définit par le caractère utilisé dans le fichier texte
        			this.addObject(i, lineNumber, ID);//Ajoute le block à la liste de block du terrain
        		}
        		lineNumber++;
        	}
        	
        	
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
        	in.close();//On vide la mémoire du fichier texte
        	file.close();//On ferme le fichier
        }
    }

}