export interface OperationModule{
  id : number;
  operationDate : Date;
  amount : number;
  type : string;
}

/* On peut construire un module d'un objet a partir de format json recupere a travers le backend,
* et pour ce la on peut utilisée le site web https://quicktype.io/typescript (Convert json to code typescript)*/
