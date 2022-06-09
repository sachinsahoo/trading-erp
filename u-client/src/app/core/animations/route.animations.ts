import {
  animate,
  query,
  style,
  transition,
  trigger,
  stagger,
  sequence,
  state
} from '@angular/animations';
import { AnimationsService } from './animations.service';

export const ROUTE_ANIMATIONS_ELEMENTS = 'route-animations-elements';

const STEPS_ALL: any[] = [
  query(':enter > *', style({ opacity: 0, position: 'fixed' }), {
    optional: true
  }),
  query(':enter .' + ROUTE_ANIMATIONS_ELEMENTS, style({ opacity: 0 }), {
    optional: true
  }),
  sequence([
    query(
      ':leave > *',
      [
        style({ transform: 'translateY(0%)', opacity: 1 }),
        animate(
          '0.2s ease-in-out',
          style({ transform: 'translateY(-3%)', opacity: 0 })
        ),
        style({ position: 'fixed' })
      ],
      { optional: true }
    ),
    query(
      ':enter > *',
      [
        style({
          transform: 'translateY(-3%)',
          opacity: 0,
          position: 'static'
        }),
        animate(
          '0.5s ease-in-out',
          style({ transform: 'translateY(0%)', opacity: 1 })
        )
      ],
      { optional: true }
    )
  ]),
  query(
    ':enter .' + ROUTE_ANIMATIONS_ELEMENTS,
    stagger(75, [
      style({ transform: 'translateY(10%)', opacity: 0 }),
      animate(
        '0.5s ease-in-out',
        style({ transform: 'translateY(0%)', opacity: 1 })
      )
    ]),
    { optional: true }
  )
];
const STEPS_NONE = [];
const STEPS_PAGE = [STEPS_ALL[0], STEPS_ALL[2]];
const STEPS_ELEMENTS = [STEPS_ALL[1], STEPS_ALL[3]];

export const routeAnimations = trigger('routeAnimations', [
  transition(isRouteAnimationsAll, STEPS_ALL),
  transition(isRouteAnimationsNone, STEPS_NONE),
  transition(isRouteAnimationsPage, STEPS_PAGE),
  transition(isRouteAnimationsElements, STEPS_ELEMENTS)
]);

export function isRouteAnimationsAll() {
  return AnimationsService.isRouteAnimationsType('ALL');
}

export function isRouteAnimationsNone() {
  return AnimationsService.isRouteAnimationsType('NONE');
}

export function isRouteAnimationsPage() {
  return AnimationsService.isRouteAnimationsType('PAGE');
}

export function isRouteAnimationsElements() {
  return AnimationsService.isRouteAnimationsType('ELEMENTS');
}

export const fadeInOutAnimations = trigger('fadeInOutAnimations', [
  transition(':enter', [
    // :enter is alias to 'void => *'
    style({ opacity: 0.1 }),
    animate(500, style({ opacity: 1 }))
  ]),
  transition(':leave', [
    // :leave is alias to '* => void'
    animate(500, style({ opacity: 0 }))
  ])
]);

export const showHideAnimations = trigger('showHideAnimations', [
  transition(':enter', [
    style({ transform: 'translateX(100%)', opacity: 0 }),
    animate('500ms', style({ transform: 'translateX(0)', opacity: 1 }))
    //animate('3s')
  ]),
  transition(':leave', [
    style({ transform: 'translateX(0)', opacity: 1 }),
    animate('500ms', style({ transform: 'translateX(100%)', opacity: 0 }))
    // animate('3s')
  ])
]);

export const simpleAnimations = trigger('simpleAnimations', [
  state('void', style({ transform: 'translateX(100%)', opacity: 0 })),
  state('*', style({ transform: 'translateX(0)', opacity: 1 })),
  transition(':enter, :leave', animate(500))
]);
