package Model;

import View.Window;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class Game implements DeletableObserver {
    private ArrayList<GameObject> terrains = new ArrayList<GameObject>();//une ArrayList pour le terrain
    private ArrayList<Entity> entities = new ArrayList<Entity>();//Et une pour les entités, permet de dessiner les entités par dessus le sol.

    private Window window;
    private int size;
    private int lineNumber;

    public Game(Window window) throws IOException {
        this.window = window;
        size = window.getSize();

        // Creating one Player at the center of the map
        entities.add(new Player(size/2, size/2, 3, 5));
        MonstreTest monstre = new MonstreTest(size/2 -5, size/2, window, this);
        monstre.attachDeletable(this);
        entities.add(monstre);

        // Map building
        this.textMapToList();

        window.setGameObjects(terrains);//Une fois le terrain et toutes les entités dans les liste,
        window.setEntities(entities);//on ajoute les listes à la map et on rafraichit la map
        notifyView();
    }
    
    /*
     * Fonction qui vérifie les condition nécéssaire au déplacement d'une entité
     */
    public void moveEntity(int x, int y, Entity entity) {
    	int nextX = entity.getPosX() + x;
        int nextY = entity.getPosY() + y;

        if (!checkObstacle(nextX, nextY)) {
            entity.move(x, y);
        }
        notifyView();
    }

    /*
     * Vérifie si il y a un obstacle empêchant le déplacement d'une entité
     */
	public boolean checkObstacle(int x, int y) {
    	boolean obstacle = false;
    	for (GameObject object : terrains) {
            if (object.isAtPosition(x, y)) {//regarde si il y a un obstacle devant
                obstacle = object.isObstacle();
            }
            if(obstacle == true) {
            	break;
            }
        }
    	for (GameObject object : entities) {
    		if(obstacle == true) {
            	break;
            }
            if (object.isAtPosition(x, y)) {//regarde si il y a un obstacle devant
                obstacle = object.isObstacle();
            }
        }
    	return obstacle;
    }
    
	/*
	 * Fonction qui vérifie si il y a quelque chose à attaquer et, le cas échéant, attaque
	 */
    public void attack(Direction direction, Entity attacker) {
    	int x = attacker.getPosX();
    	int y = attacker.getPosY();
    	switch(direction) {
    	case Left :
    		x--;
    		attacker.setDirection(Direction.Left);
    		break;
    	case Right:
    		x++;
    		attacker.setDirection(Direction.Right);
    		break;
    	case Up:
    		y--;
    		attacker.setDirection(Direction.Up);
    		break;
    	case Down:
    		y++;
    		attacker.setDirection(Direction.Down);
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
		}
		notifyView();
    }

    /*
     * Met l'affichage à jour
     */
    private void notifyView() {
        window.update();
    }

    /*
     *Supprime l'objet auquel est attaché le Deletable ps
     */
	@Override
    synchronized public void delete(Deletable ps, ArrayList<GameObject> loot) {
        entities.remove(ps);
        if (loot != null) {
            terrains.addAll(loot);
        }
        notifyView();
    }

	/*
	 * Affiche la position du joueur dans la console
	 */
    public void playerPos(int playerNumber) {
        Player player = ((Player) entities.get(playerNumber));
        System.out.println(player.getPosX() + ":" + player.getPosY());
    }
    
    /*
     * Fonction qui revoie l'objet joueur
     * (utilisé pour permettre au clavier de savoir quelle entité il dirige)
     */
    public GameObject getPlayer() {
    	return entities.get(0);
    }
    
    /*
     * Fonction qui renvoie le nombre d'entité encore présente sur la carte.
     * (utilisé pour l'ouverture de la porte)
     */
    public int getAmountEntities() {
    	return entities.size();
    }
    
    /*
     * Fonction qui ajoute les block à la liste en fonction de leur type
     * En cas de block cassable, ne pas oublier d'attacher un Deletable pour pouvoir le supprimer.
     */
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
		case 'D':
			terrains.add(new Door(x,y,this));
			break;
			
		default:
			//Créer un block par défaut
		}
    }
 
    /*
     * Fonction qui transforme la carte version texte en une liste de block
     */
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