/* Resolution d'une systeme de n equations     */
/* lineaires a n inconnues                     */
/* par la methode du pivot de Gauss            */

public class PivotGauss {

/* Methode d'affichage des valeurs contenues   */
/* dans un tableau de double a 1 indice        */
  
  static void affichageVecteur(double [] t) {
    int i;
    for ( i = 0 ; i < t.length ; i = i+1 ) {
      System.out.println((t[i]));
    }
    System.out.println();
  }

/* Methode d'affichage des valeurs contenues   */
/* dans un tableau de double a 2 indices       */
  
  static void affichageMatrice(double [][] t) {
    int i;
    int j;
    for ( i = 0 ; i < t.length ; i = i+1 ) {
    	System.out.print("(");
      for ( j = 0 ; j < t[i].length ; j = j+1 ) {
        System.out.print((t[i][j]+", "));
      }
      System.out.println(")");
    }
  }
  
/* Methode de calcul du produit d'un tableau   */
/* carre de taille n.n par un tableau          */
/* a 1 indice de taille n                      */
/* Resultat: Un tableau a 1 indice de taille n */

 
  
  static double [] produitMatriceVecteur(double [][] m,
                                         double [] v) {
    int i;
    int j;
    int n;
    n = v.length;
    double [] w = new double[n];
    for ( i = 0 ; i < n ; i = i+1 ) {
      w[i] = 0.0;
      for ( j = 0 ; j < n ; j = j+1 ) {
        w[i] = w[i] + m[i][j]*v[j]; } }
    return w;
  }
  
/////////////////////////////////////////////////

/* Methode de clonage d'un tableau             */
/* a 1 indice de double                        */

  static double [] clone(double [] t) {
    int i;
    int n = t.length;
    double [] nt = new double[n];
    for ( i = 0 ; i < n ; i = i+1 ) {
      nt[i] = t[i]; }
    return nt;
  }

/* Methode de clonage d'un tableau             */
/* a 2 indices de double                       */

  static double [][] clone(double [][] t) {
    int i;
    int j;
    int n = t.length;
    int m = t[0].length;
    double [][] nt = new double[n][m];
    for ( i = 0 ; i < n ; i = i+1 ) {
      for ( j = 0 ; j < m ; j = j+1 ) {
        nt[i][j] = t[i][j]; } }
    return nt;
  }

/* Permutation de Gauss                        */

  static void permutation(int l,double [][] m,double [] v) {
    int ll;
    int i;
    int n = v.length;
    double aux;
    ll = l;
    
    while ( m[ll][l] == 0.0 ) {
      ll = ll+1; }
    for ( i = l ; i < n ; i = i+1 ) {
      aux = m[l][i];
      m[l][i] = m[ll][i];
      m[ll][i] = aux; }
    aux = v[l];
    v[l] = v[ll];
    v[ll] = aux;
  }

/* Transformation de Gauss                     */

  static void transformation(double [][] m,
                             double [] v) {
  int n = v.length;
  int i;
  int j;
  int k;
  double facteur;
  for ( i = 1 ; i < n ; i++ ) {
    if ( m[i-1][i-1] == 0.0 )
      permutation(i-1,m,v);
    for ( j = i ; j < n ; j++ ) {
      facteur = m[j][i-1]/m[i-1][i-1];
      for ( k = i-1 ; k < n ; k++ ) {
        m[j][k] = m[j][k] - m[i-1][k]*facteur; }
      v[j] = v[j] - v[i-1]*facteur;  } }
  }

/* Extraction de Gauss                         */
  
  static double [] extraction(double [][] a,
                              double [] b) {
    int i;
    int j;
    int n = b.length;
    double [] v = new double[n];
    v[n-1] = b[n-1]/a[n-1][n-1];
    for ( i = n-2 ; i >= 0 ; i = i-1 ) {
      v[i] = b[i];
      for ( j = n-1 ; j > i ; j = j-1 ) {
        v[i] = v[i] - v[j]*a[i][j]; }
      v[i] = v[i]/a[i][i]; }
    return v;
  }
      
/* Resolution de Gauss                         */

  static double [] resolution(double [][] a,
                              double [] b) {
    double [] v;
    transformation(a,b);
    v = extraction(a,b);
    return v;
  }
  
/* Resolution de Gauss                         */
/* sur une copie des 2 tableaux en parametres  */

  static double [] resolutionGauss(double [][] a,
                                   double [] b) {
    double [][] ca = clone(a);
    double [] cb = clone(b);
    double [] v;
    v = resolution(ca,cb);
    return v;
  }

/////////////////////////////////////////////////
  
/* Programme principal                         */

  public static void main(String [] args) {
    double [][] a = { {0,1},{1,-2} };
    double [] b = { 2,0 };
    double [] v;
    double [] w;
    //Ecran.afficherln("Matrice A:");
    affichageMatrice(a);
    //Ecran.sautDeLigne();
    //Ecran.afficherln("Vecteur B:");
    affichageVecteur(b);
    //Ecran.sautDeLigne();
    v = resolutionGauss(a,b);
    //Ecran.afficherln("Vecteur V trouvé par Gauss :");
    affichageVecteur(v);
    //Ecran.sautDeLigne();
    //Ecran.afficherln("Produit A.V à comparer avec B:");
    w = produitMatriceVecteur(a,v);
    affichageVecteur(w);
  }
}