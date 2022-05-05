# OpenClassrooms

Parcours Développeur d'applications mobiles - Projet 3  
  

## Informations générales  
L'application Entrevoisins permet d'ajouter, de consulter et de mettre en favoris les profils des voisins d'un quartier afin de pouvoir demander et rendre des services entre eux.  
Chaque voisin a une fiche récapitulative de ses coordonnées et un résumé.  
  
langage utilisé: JAVA  
 
## Télécharger l'application  
Le lien de l'application : https://github.com/mcpro1109/OpenClassrooms  
Cliquer sur le bouton CODE pour télécharger le dossier ZIP ou le cloner via le terminal de commande: git clone https://github.com/mcpro1109/OpenClassrooms.git  
Importer l'application dans Android Studio: file -> new -> Import Project. 
Lancer l'application avec l'émulateur ou un téléphone connecté.
 
## Organisation  
   - ListNeighbourActivity est l'activité principale du programme. Elle est la page d'accueil lors de l'ouverture du programme. Dans cette activité nous avons deux onglets qui correspondent à deux fragments.  
    L'onglet My Neighbours récapitule la liste des voisins sous forme d'un recyclerview.  
    L'onglet Favoris reprend les voisins enregistrés comme favoris.  
    Un FloatingActionButton permet d'ajouter un voisin et d'ouvrir une autre activité.  
  
   - AddNeighbourActivity est l'activité qui permet de créer un voisin en remplissant les différents champs et d'ajouter le voisin à la liste.  
  
   - NeighbourProfileActivity est l'activité qui s'ouvre lorsque l'on clique sur un profil de la liste.  
    les différents champs sont repris afin de remplir et de donner plus d'indications sur le voisin.  
    Un FloatingActionButton ajout aux favoris qui permet d'ajouter le voisin à la liste des favoris (via le fragment FavorisFragment).  
  
## Statut   
Le projet est complet et fonctionnel.  
