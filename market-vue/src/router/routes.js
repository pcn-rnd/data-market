import store from '@/state/store'

export default [{
  path: '/',
  name: 'index',//home
  meta: {
    authRequired: true,
  },
  component: () =>
      import ('./views/data-market/index'),
},
  {
    path: '/login',
    name: 'login',
    component: () =>
        import ('./views/account/login'),
    meta: {
      beforeResolve(routeTo, routeFrom, next) {
        // If the user is already logged in
        if (store.getters['auth/loggedIn']) {
          // Redirect to the home page instead
          next({name: 'home'})
        } else {
          // Continue to the login page
          next()
        }
      },
    },
  },
  {
    path: '/logout',
    name: 'logout',
    meta: {
      authRequired: false,
      beforeResolve(routeTo, routeFrom, next) {
        store.dispatch('/auth/logOut')
        const authRequiredOnPreviousRoute = routeFrom.matched.some(
            (route) => route.push('/index')
        )
        // Navigate back to previous page, or home as a fallback
        next(authRequiredOnPreviousRoute ? {name: 'home'}
            : {...routeFrom})
      },
    },
  },
  {
    path: '/404',
    name: '404',
    component: require('./views/utility/404').default,
  },
  // Redirect any unmatched routes to the 404 page. This may
  // require some server configuration to work in production:
  // https://router.vuejs.org/en/essentials/history-mode.html#example-server-configurations
  {
    path: '*',
    redirect: '404',
  },
  {
    path: '/detail',
    name: 'Dataset Detail',
    meta: {authRequired: true},
    component: () =>
        import ('./views/data-market/ds-detail')
  },
  {
    path: '/statistics',
    name: 'Statistics',
    meta: {authRequired: true},
    component: () =>
        import ('./views/data-market/statistics')
  },
  {
    path: '/create',
    name: 'Dataset Create',
    meta: {authRequired: true},
    component: () =>
        import ('./views/data-market/create')
  },
  {
    path: '/ds/purchase',
    name: 'Purchase Dataset',
    meta: {authRequired: true},
    component: () =>
        import ('./views/data-market/purchase')
  },
  {
    path: '/ds/modify',
    name: 'Dataset Modify',
    meta: {authRequired: true},
    component: () =>
        import ('./views/data-market/modify')
  },
  {
    path: '/my-page/list',
    name: '구매한 데이터셋',
    meta: {authRequired: true},
    component: () =>
        import ('./views/data-market/set-list')
  },
  {
    path: '/my-page/list-bm',
    name: '내가 만든 데이터셋',
    meta: {authRequired: true},
    component: () =>
        import ('./views/data-market/set-list-by-me')
  },
  {
    path: '/workspace/dashboard',
    name: 'Dashboard',
    meta: {authRequired: true},
    component: () =>
        import ('./views/data-market/dashboard')
  },
  {
    path : '/cooperation/list',
    name : 'Cooperation List',
    meta : {authRequired: true},
    component: () =>
        import ('./views/cooperation/list')
  },
  {
    path: '/calendar',
    name: 'Calendar',
    meta: {authRequired: true},
    component: () =>
        import ('./views/calendar/calendar')
  },
  {
    path: '/ecommerce/products',
    name: 'Products',
    meta: {authRequired: true},
    component: () =>
        import ('./views/ecommerce/products')
  },
  {
    path: '/ecommerce/product-detail',
    name: 'Product Detail',
    meta: {authRequired: true},
    component: () =>
        import ('./views/ecommerce/product-detail')
  },
  {
    path: '/ecommerce/orders',
    name: 'Orders',
    meta: {authRequired: true},
    component: () =>
        import ('./views/ecommerce/orders')
  },
  {
    path: '/ecommerce/customers',
    name: 'Customers',
    meta: {authRequired: true},
    component: () =>
        import ('./views/ecommerce/customers')
  },
  {
    path: '/ecommerce/cart',
    name: 'Cart',
    meta: {authRequired: true},
    component: () =>
        import ('./views/ecommerce/cart')
  },
  {
    path: '/ecommerce/checkout',
    name: 'Checkout',
    meta: {authRequired: true},
    component: () =>
        import ('./views/ecommerce/checkout')
  },
  {
    path: '/ecommerce/shops',
    name: 'Shops',
    meta: {authRequired: true},
    component: () =>
        import ('./views/ecommerce/shops')
  },
  {
    path: '/ecommerce/add-product',
    name: 'Add Product',
    meta: {authRequired: true},
    component: () =>
        import ('./views/ecommerce/add-product')
  },
  {
    path: '/invoices/detail',
    name: 'Invoice Detail',
    meta: {authRequired: true},
    component: () =>
        import ('./views/invoices/detail')
  },
  {
    path: '/invoices/list',
    name: 'Invoice List',
    meta: {authRequired: true},
    component: () =>
        import ('./views/invoices/list')
  },
  {
    path: '/ui/alerts',
    name: 'Alerts',
    meta: {
      authRequired: true
    },
    component: () =>
        import ('./views/ui/alerts')
  },
  {
    path: '/ui/buttons',
    name: 'Buttons',
    meta: {
      authRequired: true
    },
    component: () =>
        import ('./views/ui/buttons')
  },
  {
    path: '/ui/cards',
    name: 'Cards',
    meta: {authRequired: true},
    component: () =>
        import ('./views/ui/cards')
  },
  {
    path: '/ui/carousel',
    name: 'Carousel',
    meta: {authRequired: true},
    component: () =>
        import ('./views/ui/carousel')
  },
  {
    path: '/ui/dropdowns',
    name: 'Dropdowns',
    meta: {authRequired: true},
    component: () =>
        import ('./views/ui/dropdowns')
  },
  {
    path: '/ui/grid',
    name: 'Grid',
    meta: {authRequired: true},
    component: () =>
        import ('./views/ui/grid')
  },
  {
    path: '/ui/images',
    name: 'Images',
    meta: {authRequired: true},
    component: () =>
        import ('./views/ui/images')
  },
  {
    path: '/ui/modals',
    name: 'Modals',
    meta: {authRequired: true},
    component: () =>
        import ('./views/ui/modals')
  },
  {
    path: '/ui/rangeslider',
    name: 'Rangeslider',
    meta: {authRequired: true},
    component: () =>
        import ('./views/ui/rangeslider')
  },
  {
    path: '/ui/progressbars',
    name: 'Progressbars',
    meta: {authRequired: true},
    component: () =>
        import ('./views/ui/progressbars')
  },
  {
    path: '/ui/sweet-alert',
    name: 'Sweet-alert',
    meta: {authRequired: true},
    component: () =>
        import ('./views/ui/sweet-alert')
  },
  {
    path: '/ui/tabs-accordions',
    name: 'Tabs-accordions',
    meta: {authRequired: true},
    component: () =>
        import ('./views/ui/tabs-accordions')
  },
  {
    path: '/ui/typography',
    name: 'Typography',
    meta: {authRequired: true},
    component: () =>
        import ('./views/ui/typography')
  },
  {
    path: '/ui/video',
    name: 'Video',
    meta: {authRequired: true},
    component: () =>
        import ('./views/ui/video')
  },
  {
    path: '/ui/general',
    name: 'General',
    meta: {authRequired: true},
    component: () =>
        import ('./views/ui/general')
  },
  {
    path: '/ui/colors',
    name: 'Colors',
    meta: {authRequired: true},
    component: () =>
        import ('./views/ui/colors')
  },
  {
    path: '/projects/grid',
    name: 'Projects Grid',
    meta: {authRequired: true},
    component: () =>
        import ('./views/projects/projects-grid')
  },
  {
    path: '/projects/list',
    name: 'Projects List',
    meta: {authRequired: true},
    component: () =>
        import ('./views/projects/projects-list')
  },
  {
    path: '/projects/overview',
    name: 'Project Overview',
    meta: {authRequired: true},
    component: () =>
        import ('./views/projects/overview')
  },
  {
    path: '/projects/create',
    name: 'Create New',
    meta: {authRequired: true},
    component: () =>
        import ('./views/projects/create')
  },
  {
    path: '/contacts/grid',
    name: 'User Grid',
    meta: {authRequired: true},
    component: () =>
        import ('./views/contacts/contacts-grid')
  },
  {
    path: '/contacts/list',
    name: 'User List',
    meta: {authRequired: true},
    component: () =>
        import ('./views/contacts/contacts-list')
  },
  {
    path: '/contacts/profile',
    name: 'Profile',
    meta: {authRequired: true},
    component: () =>
        import ('./views/contacts/contacts-profile')
  },
  {
    path: '/charts/apex',
    name: 'Apex chart',
    meta: {authRequired: true},
    component: () =>
        import ('./views/charts/apex')
  },
  {
    path: '/charts/chartjs',
    name: 'Chartjs chart',
    meta: {authRequired: true},
    component: () =>
        import ('./views/charts/chartjs/index')
  },
  {
    path: '/charts/chartist',
    name: 'Chartist chart',
    meta: {authRequired: true},
    component: () =>
        import ('./views/charts/chartist')
  },
  {
    path: '/icons/boxicons',
    name: 'Boxicons Icon',
    meta: {authRequired: true},
    component: () =>
        import ('./views/icons/boxicons')
  },
  {
    path: '/icons/materialdesign',
    name: 'Materialdesign Icon',
    meta: {authRequired: true},
    component: () =>
        import ('./views/icons/materialdesign')
  },
  {
    path: '/icons/dripicons',
    name: 'Dripicons Icon',
    meta: {authRequired: true},
    component: () =>
        import ('./views/icons/dripicons')
  },
  {
    path: '/icons/fontawesome',
    name: 'Fontawesome Icon',
    meta: {authRequired: true},
    component: () =>
        import ('./views/icons/fontawesome')
  },
  {
    path: '/document/documents',
    name: 'Documents',
    meta: {authRequired: true},
    component: () =>
        import ('./views/document/documents')
  },
  {
    path: '/workspace/workspace',
    name: 'Workspace',
    meta: {authRequired: true},
    component: () =>
        import ('./views/data-market/index')
  },
  {
    path: '/form/advanced',
    name: 'Form Advanced',
    meta: {authRequired: true},
    component: () =>
        import ('./views/forms/advanced')
  },
  {
    path: '/form/elements',
    name: 'Form Elements',
    meta: {authRequired: true},
    component: () =>
        import ('./views/forms/elements')
  },
  {
    path: '/form/editor',
    name: 'CK Editor',
    meta: {authRequired: true},
    component: () =>
        import ('./views/forms/ckeditor')
  },
  {
    path: '/form/uploads',
    name: 'File Uploads',
    meta: {authRequired: true},
    component: () =>
        import ('./views/forms/uploads')
  },
  {
    path: '/form/validation',
    name: 'Form Validation',
    meta: {authRequired: true},
    component: () =>
        import ('./views/forms/validation')
  },
  {
    path: '/form/wizard',
    name: 'Form Wizard',
    meta: {authRequired: true},
    component: () =>
        import ('./views/forms/wizard')
  },
  {
    path: '/form/repeater',
    name: 'Form Repeater',
    meta: {authRequired: true},
    component: () =>
        import ('./views/forms/repeater')
  },
  {
    path: '/form/mask',
    name: 'Form Mask',
    meta: {authRequired: true},
    component: () =>
        import ('./views/forms/mask')
  },
  {
    path: '/tables/basictable',
    name: 'Basic table',
    meta: {authRequired: true},
    component: () =>
        import ('./views/tables/basictable')
  },
  {
    path: '/tables/advancedtable',
    name: 'Advanced Table',
    meta: {authRequired: true},
    component: () =>
        import ('./views/tables/advancedtable')
  },
  {
    path: '/pages/starter',
    name: 'Starter',
    meta: {authRequired: true},
    component: () =>
        import ('./views/utility/starter')
  },
  {
    path: '/pages/maintenance',
    name: 'Maintenance',
    meta: {authRequired: true},
    component: () =>
        import ('./views/utility/maintenance')
  },
  {
    path: '/pages/timeline',
    name: 'Timeline',
    meta: {authRequired: true},
    component: () =>
        import ('./views/utility/timeline')
  },
  {
    path: '/pages/faqs',
    name: 'FAQs',
    meta: {authRequired: true},
    component: () =>
        import ('./views/utility/faqs')
  },
  {
    path: '/pages/pricing',
    name: 'Pricing',
    meta: {authRequired: true},
    component: () =>
        import ('./views/utility/pricing')
  },
  {
    path: '/pages/404',
    name: 'Error-404',
    meta: {authRequired: true},
    component: () =>
        import ('./views/utility/404')
  },
  {
    path: '/pages/500',
    name: 'Error-500',
    meta: {authRequired: true},
    component: () =>
        import ('./views/utility/500')
  },
  {
    path: '/email/inbox',
    name: 'Inbox',
    meta: {authRequired: true},
    component: () =>
        import ('./views/email/inbox')
  },
  {
    path: '/email/reademail',
    name: 'Read Email',
    meta: {authRequired: true},
    component: () =>
        import ('./views/email/reademail')
  },
  {
    path: '/tasks/list',
    name: 'Task list',
    meta: {authRequired: true},
    component: () =>
        import ('./views/tasks/task-list')
  },
  {
    path: '/tasks/kanban',
    name: 'Kanbanboard',
    meta: {authRequired: true},
    component: () =>
        import ('./views/tasks/kanbanboard')
  },
  {
    path: '/tasks/create',
    name: 'Create Task',
    meta: {authRequired: true},
    component: () =>
        import ('./views/tasks/task-create')
  },
  {
    path: '/layout/horizontal',
    name: 'Horizontal',
    meta: {authRequired: true},
    component: () =>
        import ('./views/layout/horizontal')
  },
  {
    path: '/auth/login-1',
    name: 'Login sample',
    meta: {authRequired: true},
    component: () =>
        import ('./views/sample-pages/login-sample')
  },
  {
    path: '/auth/register-1',
    name: 'Register sample',
    meta: {authRequired: true},
    component: () =>
        import ('./views/sample-pages/register-sample')
  },
  {
    path: '/auth/recoverpw',
    name: 'Recover pwd',
    meta: {authRequired: true},
    component: () =>
        import ('./views/sample-pages/recoverpw-sample')
  },
  {
    path: '/auth/lock-screen',
    name: 'Lock screen',
    meta: {authRequired: true},
    component: () =>
        import ('./views/sample-pages/lockscreen')
  }
]
