import { HttpInterceptorFn, HttpResponse } from '@angular/common/http';
import { tap } from 'rxjs/operators';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const token = localStorage.getItem('token');
  if (token) {
    req = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`,
      },
    });
  }
  console.log('Request:', req);

  return next(req).pipe(
    tap({
      next: (event) => {
        console.log('Response:', event);
        if (event instanceof HttpResponse) {
          const token = event.headers.get('access_token');
          console.log(token);
          console.log('event', event.headers);

          if (token) {
            localStorage.setItem('token', token);
            console.log('token', token);
          }
        }
      },
      error: (error) => {
        console.error('Error:', error);
      },
    })
  );
};
