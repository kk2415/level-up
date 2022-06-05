import React from 'react'
import Header from './Header'
import Footer from './Footer'
import {Container} from 'react-bootstrap'

const Layout = ( {children} ) => {
  return (
	<>
		<Header name="klyunkim"/>
			{children}
		<Footer />
	</>
  )
}

export default Layout