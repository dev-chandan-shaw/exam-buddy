import { first } from 'rxjs';

export interface ILoginUser {
  email: string;
  password: string;
}

export interface IUser {
  id: number;
  firstName: string;
  lastName: string;
  email: string;
  accessToken: string;
}

export interface ICreateUser {
  firstName: string;
  lastName: string;
  email: string;
  password: string;
}
