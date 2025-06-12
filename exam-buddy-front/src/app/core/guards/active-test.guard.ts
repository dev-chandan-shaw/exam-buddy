import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { ActiveTestService } from 'app/features/ongoing-test/services/active-est.service';

export const activeTestGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);
  const activeTestService = inject(ActiveTestService);
  if (!activeTestService.isTestActive()) {
    router.navigate(['exam']);
    return false;
  }
  return true;
};
