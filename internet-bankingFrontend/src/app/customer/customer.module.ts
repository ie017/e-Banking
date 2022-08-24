export interface CustomerModule {
  id : number;
  name : string;
  email : string;
}
/* We can replace interface by class but we have to initialise
 our attributes or use some techniques like id? : number that mean
 id should declare without initialisation or user !, or give them value as default
 like id : number | null=null or id : number | undefined*/
